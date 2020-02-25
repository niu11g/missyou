package com.lin.missyou.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Banner extends BaseEntity {
    @Id
    private Long id;
    private String name;
    private String description;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;
    @JsonIgnore
    private Date deleteTime;
    private String title;
    private String img;

    //懒加载设置成急加载
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = ("bannerId"))
    private List<BannerItem> items;
}
