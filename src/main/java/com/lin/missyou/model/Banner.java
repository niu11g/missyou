package com.lin.missyou.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Where(clause = " delete_time is null ")
public class Banner extends BaseEntity {
    @Id
    private Long id;
    private String name;
    private String description;
    private String title;
    private String img;

    //懒加载设置成急加载
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = ("bannerId"))
    private List<BannerItem> items;
}
