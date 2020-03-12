package com.lin.missyou.model;

import com.lin.missyou.util.SuperGenericAndJson;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Setter
@Getter
public class Sku extends BaseEntity {
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
//  @Convert(converter = MapAndJson.class)
//  private Map<String,Object> test;

//  @Convert(converter = ListAndJson.class)
    @Convert(converter = SuperGenericAndJson.class)
    private List<Spec> specs;

//    public List<Spec> getSpecs() {
//        if (this.specs == null) {
//            return Collections.emptyList();
//        }
//        return GenericAndJson.jsonToList(this.specs);
//    }
//
//    public void setSpecs(List<Spec> specs) {
//        if (specs.isEmpty()) {
//            return;
//        }
//        this.specs = GenericAndJson.objectToJson(specs);
//    }

    private String code;
    private Long stock;

}
