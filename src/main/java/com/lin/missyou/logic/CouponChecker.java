package com.lin.missyou.logic;

import com.lin.missyou.bo.SkuOrderBO;
import com.lin.missyou.core.enumeration.CouponType;
import com.lin.missyou.core.money.IMoneyDiscount;
import com.lin.missyou.exception.http.ForbiddenException;
import com.lin.missyou.exception.http.ParameterException;
import com.lin.missyou.model.Coupon;
import com.lin.missyou.model.UserCoupon;
import com.lin.missyou.util.CommonUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CouponChecker {

    private Coupon coupon;
    private UserCoupon userCoupon;
    private IMoneyDiscount iMoneyDiscount;

    public CouponChecker(Coupon coupon, UserCoupon userCoupon, IMoneyDiscount iMoneyDiscount){
        this.iMoneyDiscount = iMoneyDiscount;
        this.coupon = coupon;
        this.userCoupon = userCoupon;
    }

    public void isOK(){
        Date now = new Date();
        Boolean isInTimeline = CommonUtil.isInTimeLine(now,this.coupon.getStartTime(),this.coupon.getEndTime());
        if(!isInTimeline){
            throw new ForbiddenException(40007);
        }

    }

    public void finalTotalPriceIsOk(BigDecimal orderFinalTotalPrice,BigDecimal serverTotalPrice){
        BigDecimal serverFinalTotalPrice;
        switch (CouponType.toType(this.coupon.getType())) {
            case FULL_MINUS:
            case NO_THRESHOLD_MINUS:
                //服务端实际支付=前端传送过来的总金额-服务端减多少
                serverFinalTotalPrice = serverTotalPrice.subtract(this.coupon.getMinus());
                if(serverFinalTotalPrice.compareTo(new BigDecimal("0")) <= 0){
                    throw new ForbiddenException(50008);
                }
                break;
            case FULL_OFF:
                serverFinalTotalPrice = iMoneyDiscount.discount(serverTotalPrice,this.coupon.getRate());
                break;
            default:
                throw new ParameterException(40009);
        }
        //服务端的实际支付与前端传送过来的对比
        int compare = serverFinalTotalPrice.compareTo(orderFinalTotalPrice);
        if (compare != 0) {
            throw new ForbiddenException(50008);
        }
    }

    public void canBeUsed(List<SkuOrderBO> skuOrderBOList, BigDecimal serverTotalPrice){
        BigDecimal orderCategoryPrice;
        if(this.coupon.getWholeStore()){
            orderCategoryPrice = serverTotalPrice;
        }

    }

    private BigDecimal getSumByCategory(List<SkuOrderBO> skuOrderBOList,Long cid){
        BigDecimal sum = skuOrderBOList.stream()
                .filter(sku->sku.getCategoryId().equals(cid))
                .map(bo->bo.getTotalPrice())
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0"));
        return sum;
    }

//    public CouponChecker(Long couponId,Long uid){
//
//    }
}
