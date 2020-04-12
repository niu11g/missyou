package com.lin.missyou.vo;

import com.lin.missyou.model.Coupon;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CouponCategoryVO extends CouponPureVO {
    private List<CategoryPureVo> categories;

    public CouponCategoryVO(Coupon coupon){
        super(coupon);
        categories = CategoryPureVo.getList(coupon.getCategoryList());
    }


}
