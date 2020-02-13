package com.lin.missyou.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Spu {
    @Id
    private Long id;
    private String title;
    private String subtitle;

    @ManyToMany(mappedBy = "spuList")
    private List<Theme> themeList;


}
