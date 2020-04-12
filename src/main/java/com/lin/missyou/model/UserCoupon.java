package com.lin.missyou.model;

import com.lin.missyou.core.enumeration.LoginType;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//需要直接操作这张表，具有实际业务意义，所以需要建立这张业务表，而Activity与Coupon没有实际业务意义，则不需要
public class UserCoupon{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;         //用户ID
    private Long couponId;       //优惠劵ID
    private Date createTime;     //用户领取优惠劵时间
    private Integer status;      //用户使用优惠劵的状态：已领取未使用 已领取已使用 已过期
    private Long orderId;        //订单ID
}
