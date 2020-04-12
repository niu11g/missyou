package com.lin.missyou.Service;

import com.lin.missyou.core.enumeration.CouponStatus;
import com.lin.missyou.exception.http.NotFoundException;
import com.lin.missyou.exception.http.ParameterException;
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
                .ifPresent((uc)->{throw new ParameterException(40006);});
//                .orElseThrow(()->new ParameterException(40006));

        UserCoupon userCouponNew = UserCoupon.builder()
                .userId(uid)
                .couponId(couponId)
                .status(CouponStatus.AVAILABLE.getValue())
                .createTime(now)
                .build();
        userCouponRepository.save(userCouponNew);
    }
    //获取未使用的优惠劵
    public List<Coupon> getMyAvailableCoupons(Long uid){
        Date now = new Date();
        return this.couponRepository.findMyAvailable(uid,now);
    }
    //获取已使用的优惠劵
    public List<Coupon> getMyUsedCoupons(Long uid){
        return this.couponRepository.findMyUsed(uid);
    }
    //获取已过期的优惠劵
    public List<Coupon> getMyExpiredCoupons(Long uid){
        Date now = new Date();
       return this.couponRepository.findMyExpired(uid,now);
    }

}
