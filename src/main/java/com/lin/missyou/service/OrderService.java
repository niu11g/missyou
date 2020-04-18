package com.lin.missyou.service;

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
import com.lin.missyou.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
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

        String orderNo = OrderUtil.makeOrderNo();
        Order order = Order.builder()
                .orderNo(orderNo)
                .totalPrice(orderDTO.getTotalPrice())
                .finalTotalPrice(orderDTO.getFinalTotalPrice())
                .userId(uid)
                .totalCount(orderChecker.getTotalCount())
                .snapImg(orderChecker.getLeaderImg())
                .snapTitle(orderChecker.getLeaderTitle())
                .status(OrderStatus.UNPAID.value())
                .build();
        order.setSnapAddress(orderDTO.getAddress());
        order.setSnapItems(orderChecker.getOrderSkuList());
        this.orderRepository.save(order);
        //减库存
        this.reduceStock(orderChecker);
        //核销优惠劵
        if(orderDTO.getCouponId() != null){
            this.writeOffCoupon(orderDTO.getCouponId(),order.getId(),uid);
        }
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
}
