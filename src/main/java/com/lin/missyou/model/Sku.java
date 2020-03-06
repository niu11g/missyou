package com.lin.missyou.model;

import com.lin.missyou.util.ListAndJson;
import com.lin.missyou.util.MapAndJson;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Setter
@Getter
public class Sku extends BaseEntity{
    @Id
    private Long id;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Boolean online;
    private String img;
    private String title;
    private Long spuId;
    private Long categoryId;
    private Long rootCategoryId;
//    private Map<String,Object> test;
      @Convert(converter = MapAndJson.class)
      private Map<String,Object> test;

    @Convert(converter = ListAndJson.class)
    private List<Object> specs;
    private String code;
    private Long stock;

}
