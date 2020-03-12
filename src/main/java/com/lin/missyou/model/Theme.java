package com.lin.missyou.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@Where(clause = "delete_time is null")
public class Theme extends BaseEntity {
    @Id
    private Long id;
    private String title;
    private String description;
    private String name;
    private String entranceImg;
    private String extend;
    private String internalTopImg;
    private String titleImg;
    private Boolean online;
    private String tplName;

    //导航关系
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="theme_spu",joinColumns = @JoinColumn(name="theme_id"),
            inverseJoinColumns = @JoinColumn(name="spu_id"))
    private List<Spu> spuList;
}
