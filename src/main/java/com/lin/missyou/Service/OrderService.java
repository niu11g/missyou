package com.lin.missyou.Service;

import com.lin.missyou.core.money.IMoneyDiscount;
import com.lin.missyou.dto.OrderDTO;
import com.lin.missyou.dto.SkuInfoDTO;
import com.lin.missyou.exception.http.NotFoundException;
import com.lin.missyou.exception.http.ParameterException;
import com.lin.missyou.logic.CouponChecker;
import com.lin.missyou.logic.OrderChecker;
import com.lin.missyou.model.Coupon;
import com.lin.missyou.model.Sku;
import com.lin.missyou.model.UserCoupon;
import com.lin.missyou.repository.CouponRepository;
import com.lin.missyou.repository.UserCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    @Value("${missyou.order.max-sku-limit}")
    private int maxSkuLimit;

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

            UserCoupon userCoupon = this.userCouponRepository.findFirstByUserIdAndCouponId(uid,couponId)
                    .orElseThrow(() -> new NotFoundException(50006));
            couponChecker = new CouponChecker(coupon,iMoneyDiscount);
        }
        OrderChecker orderChecker = new OrderChecker(
                orderDTO,skuList,couponChecker,this.maxSkuLimit
        );
        orderChecker.isOK();
        return orderChecker;
    }
}
