package com.lin.missyou.model;

import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.description.ModifierReviewable;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Setter
@Getter
@Where(clause = "delete_time is null and online = 1")
public class Activity extends BaseEntity {
    @Id
    private Long id;
    private String title;
    private String description;
    private Date startTime;        //领取优惠劵的开始日期
    private Date endTime;          //领取优惠劵的结束日期
    private String remark;         //活动说明
    private Boolean online;        //是否线上
    private String entranceImg;    //活动封面图片
    private String internalTopImg; //活动顶部图片
    private String name;           //活动名称

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = ("activityId"))
    private List<Coupon> couponList;

    //一个多对多关系可以转化成两个一对多关系。
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "activity_coupon",
//            joinColumns = @JoinColumn(name="activity_id"),
//            inverseJoinColumns = @JoinColumn(name="coupon_id"))
//    private List<Coupon> couponList;

}
