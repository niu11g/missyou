package com.lin.missyou.api.V1;

import com.lin.missyou.Service.CouponService;
import com.lin.missyou.core.LocalUser;
import com.lin.missyou.core.interceptors.ScopeLevel;
import com.lin.missyou.model.Coupon;
import com.lin.missyou.vo.CouponPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @GetMapping("/by/Category/{cid}")
    //根据分类查找优惠劵
    public List<CouponPureVO> getCouponList(@PathVariable Long cid){
        List<Coupon> coupons = couponService.getByCategory(cid);
        if(coupons.isEmpty()){
            return Collections.emptyList();
        }
        List<CouponPureVO> vos = CouponPureVO.getList(coupons);
        return vos;
    }

    @GetMapping("/whole_store")
    public List<CouponPureVO> getWholeStoreCouponList(){
        List<Coupon> coupons = this.couponService.getWholeStoreCoupons(true);
        if(coupons.isEmpty()){
            Collections.emptyList();
        }
        List<CouponPureVO> vos = CouponPureVO.getList(coupons);
        return vos;
    }

    @ScopeLevel()
    @PostMapping("/collect/{id}")
    public void collectCoupon(@PathVariable Long id){
        Long uid = LocalUser.getUser().getId();
        couponService.collectOneCoupon(uid,id);
    }
}
