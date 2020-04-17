package com.lin.missyou.logic;

import com.lin.missyou.bo.SkuOrderBO;
import com.lin.missyou.core.enumeration.CouponType;
import com.lin.missyou.core.money.IMoneyDiscount;
import com.lin.missyou.exception.http.ForbiddenException;
import com.lin.missyou.exception.http.ParameterException;
import com.lin.missyou.model.Category;
import com.lin.missyou.model.Coupon;
import com.lin.missyou.model.UserCoupon;
import com.lin.missyou.util.CommonUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CouponChecker {

    private Coupon coupon;
//    private UserCoupon userCoupon ;
    private IMoneyDiscount iMoneyDiscount;

    public CouponChecker(Coupon coupon, IMoneyDiscount iMoneyDiscount){
        this.iMoneyDiscount = iMoneyDiscount;
        this.coupon = coupon;
//        this.userCoupon = userCoupon;
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

    public void canBeUsed(List<SkuOrderBO> skuOrderBOList,BigDecimal serverTotalPrice){
        BigDecimal orderCategoryPrice;
        if(this.coupon.getWholeStore()){
            orderCategoryPrice = serverTotalPrice;
        }else{
            //查询此优惠劵适用于哪些分类
            List<Long> cidList = this.coupon.getCategoryList()
                    .stream()
                    .map(Category::getId)
                    .collect(Collectors.toList());
            orderCategoryPrice = this.getSumByCategoryList(skuOrderBOList,cidList);
        }
        this.couponCanBeUsed(orderCategoryPrice);
    }

    private void couponCanBeUsed(BigDecimal orderCategoryPrice){
        switch(CouponType.toType(this.coupon.getType())){
            case FULL_OFF:
            case FULL_MINUS:
                int compare = this.coupon.getFullMoney().compareTo(orderCategoryPrice);
                //如果没有满足满减额度
                if(compare>0){
                    throw new ParameterException(40008);
                }
                break;
            case NO_THRESHOLD_MINUS:
                break;
            default:
                throw new ParameterException(40009);
        }
    }
    //计算某些分类下的商品订单金额
    private BigDecimal getSumByCategoryList(List<SkuOrderBO> skuOrderBOList,List<Long> cidList){
        BigDecimal sum = cidList.stream()
                .map(cid->this.getSumByCategory(skuOrderBOList,cid))
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0"));
        return sum;
    }
    //计算某个分类下的商品订单金额
    private BigDecimal getSumByCategory(List<SkuOrderBO> skuOrderBOList,Long cid){

        BigDecimal sum = skuOrderBOList.stream()
                .filter(sku->sku.getCategoryId().equals(cid))
                .map(sku->sku.getTotalPrice())
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0"));
        return sum;

    }
}
