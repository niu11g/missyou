package com.lin.missyou.api.v1;

import com.lin.missyou.service.CouponService;
import com.lin.missyou.core.LocalUser;
import com.lin.missyou.core.UnifyResponse;
import com.lin.missyou.core.enumeration.CouponStatus;
import com.lin.missyou.core.interceptors.ScopeLevel;
import com.lin.missyou.exception.http.ParameterException;
import com.lin.missyou.model.Coupon;
import com.lin.missyou.model.User;
import com.lin.missyou.vo.CouponCategoryVO;
import com.lin.missyou.vo.CouponPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        UnifyResponse.createSuccess(0);
    }

    @ScopeLevel()
    @GetMapping("/myself/by/status/{status}")
    public List<CouponPureVO> getMyCouponByStatus(@PathVariable Integer status){
        Long uid = LocalUser.getUser().getId();
        List<Coupon> couponList;
        switch (CouponStatus.toType(status)){
            case AVAILABLE:
                couponList = couponService.getMyAvailableCoupons(uid);
                break;
            case USED:
                couponList = couponService.getMyUsedCoupons(uid);
                break;
            case EXPIRED:
                couponList = couponService.getMyExpiredCoupons(uid);
                break;
            default:
                throw new ParameterException(40001);

        }
        return CouponPureVO.getList(couponList);
    }
    //获取用户可使用优惠劵的分类
    @ScopeLevel
    @GetMapping("/myself/available/with_category")
    public List<CouponCategoryVO> getUserCouponWithCategory(){
        User user = LocalUser.getUser();
        //获取可使用的优惠劵
        List<Coupon> coupons = couponService.getMyAvailableCoupons(user.getId());
        if(coupons.isEmpty()){
            return Collections.emptyList();
        }
        return coupons.stream().map(CouponCategoryVO::new)
                .collect(Collectors.toList());
    }
}
