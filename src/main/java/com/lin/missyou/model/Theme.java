package com.lin.missyou.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Theme {
    @Id
    private Long id;
    private String title;
    private String name;
    @ManyToMany
    @JoinTable(name=("theme_spu"), joinColumns = @JoinColumn(name=("theme_id")),
            inverseJoinColumns = @JoinColumn(name=("spu_id")))
    private List<Spu> spuList;
}
