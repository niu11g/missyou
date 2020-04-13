package com.lin.missyou.core.enumeration;

import java.util.stream.Stream;

public enum CouponStatus {
    //优惠劵状态
    AVAILABLE(1,"可以使用，未过期"),  //未使用
    USED(2,"已使用"),               //已使用
    EXPIRED(3,"未使用，已过期");     //已过期

    private Integer value;

    public Integer getValue(){
        return this.value;
    }

    CouponStatus(Integer value,String description){
        this.value = value;
    }

    public static CouponStatus toType(int value){
        return Stream.of(CouponStatus.values())
                .filter(c -> c.value == value)
                .findAny()
                .orElse(null);
    }

}
