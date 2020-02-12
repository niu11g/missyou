package com.lin.missyou.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = ("banner1"))
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 16)
    private String name;

//  @Transient  不需要映射到物理表中的字段
    @Transient
    private String description;
    private String img;
    private String title;

    @OneToMany
    @JoinColumn(name=("bannerId"))
    private List<BannerItem> items;
}
