package com.lin.missyou.service;

import com.lin.missyou.core.LocalUser;
import com.lin.missyou.core.enumeration.OrderStatus;
import com.lin.missyou.core.money.IMoneyDiscount;
import com.lin.missyou.dto.OrderDTO;
import com.lin.missyou.dto.SkuInfoDTO;
import com.lin.missyou.exception.http.ForbiddenException;
import com.lin.missyou.exception.http.NotFoundException;
import com.lin.missyou.exception.http.ParameterException;
import com.lin.missyou.logic.CouponChecker;
import com.lin.missyou.logic.OrderChecker;
import com.lin.missyou.model.*;
import com.lin.missyou.repository.CouponRepository;
import com.lin.missyou.repository.OrderRepository;
import com.lin.missyou.repository.SkuRepository;
import com.lin.missyou.repository.UserCouponRepository;
import com.lin.missyou.util.CommonUtil;
import com.lin.missyou.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private SkuService skuService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private IMoneyDiscount iMoneyDiscount;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Value("${missyou.order.max-sku-limit}")
    private int maxSkuLimit;

    @Value("${missyou.order.pay-time-limit}")
    private Integer payTimeLimit;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Transactional
    public OrderChecker isOK(Long uid, OrderDTO orderDTO){
        if(orderDTO.getFinalTotalPrice().compareTo(new BigDecimal("0")) <= 0){
            throw new ParameterException(50011);
        }

        List<Long> skuIdList = orderDTO.getSkuInfoList()
                .stream()
                .map(SkuInfoDTO::getId)
                .collect(Collectors.toList());

        List<Sku> skuList = skuService.getSkuListByIds(skuIdList);

        //OrderChecker CouponChecker
        Long couponId = orderDTO.getCouponId();
        CouponChecker couponChecker = null;
        if(couponId != null){
            Coupon coupon = this.couponRepository.findById(couponId)
                    .orElseThrow(() -> new NotFoundException(40004));

            UserCoupon userCoupon = this.userCouponRepository.findFirstByUserIdAndCouponIdAndStatus(uid,couponId,1)
                    .orElseThrow(() -> new NotFoundException(50006));
            couponChecker = new CouponChecker(coupon,iMoneyDiscount);
        }
        OrderChecker orderChecker = new OrderChecker(
                orderDTO,skuList,couponChecker,this.maxSkuLimit
        );
        orderChecker.isOK();
        return orderChecker;
    }

    //里面包含连续新增、更改操作，并且是不同的表，需要加事务 一个步骤执行有问题，全部回滚
    @Transactional
    public Long placeOrder(Long uid,OrderDTO orderDTO,OrderChecker orderChecker){
        //build()无法读取基类上的属性
        String orderNo = OrderUtil.makeOrderNo();
        Calendar now = Calendar.getInstance();
        Calendar now1 = (Calendar) now.clone();
        Date expiredTime = CommonUtil.addSomeSeconds(now,this.payTimeLimit).getTime();
        Order order = Order.builder()
                .orderNo(orderNo)
                .totalPrice(orderDTO.getTotalPrice())
                .finalTotalPrice(orderDTO.getFinalTotalPrice())
                .userId(uid)
                .totalCount(orderChecker.getTotalCount())
                .snapImg(orderChecker.getLeaderImg())
                .snapTitle(orderChecker.getLeaderTitle())
                .status(OrderStatus.UNPAID.value())
                .expiredTime(expiredTime)
                .placedTime(now1.getTime())
                .build();
        order.setCreateTime(now1.getTime());
        order.setSnapAddress(orderDTO.getAddress());
        order.setSnapItems(orderChecker.getOrderSkuList());
        this.orderRepository.save(order);
        //减库存
        this.reduceStock(orderChecker);
        //核销优惠劵
        Long couponId = -1L;
        if(orderDTO.getCouponId() != null){
            this.writeOffCoupon(orderDTO.getCouponId(),order.getId(),uid);
            couponId = orderDTO.getCouponId();
        }
        this.sendToRedis(order.getId(),uid,couponId);
        //加入到延迟消息队列
        return order.getId();
    }

    private void writeOffCoupon(Long couponId,Long oid,Long uid){
        int result = this.userCouponRepository.writeOff(couponId,oid,uid);
        if(result!=1){
            throw new ForbiddenException(40012);
        }
    }

    private void reduceStock(OrderChecker orderChecker){
        List<OrderSku> orderSkuList = orderChecker.getOrderSkuList();
        for(OrderSku orderSku : orderSkuList){
            int result = this.skuRepository.reduceStock(orderSku.getId(),orderSku.getCount());
            if(result != 1){
                throw new ParameterException(50003);
            }
        }

    }

    public Page<Order> getUnpaid(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page,size, Sort.by("createTime").descending());
        Long uid = LocalUser.getUser().getId();
        Date now = new Date();
        return this.orderRepository.findByExpiredTimeGreaterThanAndStatusAndUserId(now,OrderStatus.UNPAID.value(),uid,pageable);
    }

    public Page<Order> getByStatus(Integer status,Integer page,Integer size){
        Pageable pageable = PageRequest.of(page,size,Sort.by("createTime").descending());
        Long uid = LocalUser.getUser().getId();
        if(status == OrderStatus.ALL.value()){
            return this.orderRepository.findByUserId(uid,pageable);
        }
        return this.orderRepository.findByUserIdAndStatus(uid,status,pageable);
    }

    public Optional<Order> getOrderDetail(Long oid){
        Long uid = LocalUser.getUser().getId();
        return this.orderRepository.findFirstByUserIdAndId(uid,oid);
    }

    public void updateOrderPrepayId(Long orderId,String prePayId){
        Optional<Order> order = this.orderRepository.findById(orderId);
        order.ifPresent(o->{
            o.setPrepayId(prePayId);
            this.orderRepository.save(o);
        });
        order.orElseThrow(()->new ParameterException(10007));
    }

    private void sendToRedis(Long oid,Long uid,Long couponId){
        String key =uid.toString()+","+oid.toString()+","+couponId.toString();

        try {
            stringRedisTemplate.opsForValue().set(key,"1",this.payTimeLimit);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
