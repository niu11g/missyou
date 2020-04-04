package com.lin.missyou.model;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Setter
@Getter
@Where(clause = "delete_time is null")
public class Coupon extends BaseEntity {
    @Id
    private Long id;
    private Long activityId;
    private String title;           //优惠卷的标题
    private Date startTime;         //有效开始日期
    private Date endTime;           //有效结束日期
    private String description;     //优惠卷的描述
    private BigDecimal fullMoney;   //满金额
    private BigDecimal minus;       //减多少
    private BigDecimal rate;        //折扣
    //优惠卷的类型：满减劵，满减折扣劵，折扣卷，无门槛劵，有效期劵（领取时间开始30天内有效）-加一有效期天数的字段，领取日+有效期天数判断是否过期
    private Integer type;
    private String remark;          //优惠劵的说明
    private Boolean wholeStore;     //是否为全场劵

    @ManyToMany(fetch = FetchType.LAZY,mappedBy = ("couponList"))  //? couponList
    private List<Category> categoryList;

//    @ManyToMany(fetch = FetchType.LAZY,mappedBy = ("couponList"))
//    private List<Activity> activityList;
}
