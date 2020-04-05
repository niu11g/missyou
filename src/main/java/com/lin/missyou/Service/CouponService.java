package com.lin.missyou.Service;

import com.lin.missyou.model.Coupon;
import com.lin.missyou.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CouponService {

    @Autowired
    CouponRepository couponRepository;

    public List<Coupon> getByCategory(Long cid){
        Date now = new Date();
        return couponRepository.findByCategory(cid,now);
    }

    public List<Coupon> getWholeStoreCoupons(Boolean isWholeStore){
        Date now = new Date();
        return couponRepository.findByWholStore(isWholeStore,now);
    }


}
