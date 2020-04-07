package com.lin.missyou.Service;

import com.lin.missyou.core.enumeration.CouponStatus;
import com.lin.missyou.core.enumeration.LoginType;
import com.lin.missyou.exception.NotFoundException;
import com.lin.missyou.exception.ParameterException;
import com.lin.missyou.model.Activity;
import com.lin.missyou.model.Coupon;
import com.lin.missyou.model.UserCoupon;
import com.lin.missyou.repository.ActivityRepository;
import com.lin.missyou.repository.CouponRepository;
import com.lin.missyou.repository.UserCouponRepository;
import com.lin.missyou.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CouponService {

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    UserCouponRepository userCouponRepository;

    public List<Coupon> getByCategory(Long cid){
        Date now = new Date();
        return couponRepository.findByCategory(cid,now);
    }

    public List<Coupon> getWholeStoreCoupons(Boolean isWholeStore){
        Date now = new Date();
        return couponRepository.findByWholStore(isWholeStore,now);
    }

    public void collectOneCoupon(Long uid,Long couponId){
        //如果couponId不存在，则抛出不存在
        this.couponRepository
                .findById(couponId)
                .orElseThrow(()->new NotFoundException(40004));

        Activity activity = this.activityRepository
                .findByCouponListId(couponId)
                .orElseThrow(()->new NotFoundException(40010));

        Date now = new Date();
        Boolean isIn = CommonUtil.isInTimeLine(now,activity.getStartTime(),activity.getEndTime());
        if(!isIn){
            throw new ParameterException(40005);
        }
        this.userCouponRepository
                .findFirstByUserIdAndCouponId(uid,couponId)
                .orElseThrow(()->new ParameterException(40006));

        UserCoupon userCouponNew = UserCoupon.builder()
                .userId(uid)
                .couponId(couponId)
                .status(CouponStatus.AVAILABLE.getValue())
                .createTime(now)
                .build();
        userCouponRepository.save(userCouponNew);

    }


}
