package com.lin.missyou.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpuSimpleVo {
    private Long id;
    private String title;
    private String subtitle;
    private Long categoryId;
    private Long rootCategoryId;
    private Boolean online;
    private String price;
    private Long sketchSpecId;
    private Long defaultSkuId;
    private String img;
    private String discountPrice;
    private String description;
    private String tags;
    private Boolean isTest;
}
