package com.lin.missyou.vo;

import com.lin.missyou.model.Category;
import com.lin.missyou.model.Coupon;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Id;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CategoryPureVo {
    private Long id;
    private String name;
    private String description;
    private Boolean isRoot;
    private Long parentId;
    private String img;
    private Long index;

    public CategoryPureVo(Category category) {
        //基本属性拷贝
        BeanUtils.copyProperties(category,this);
    }

    public static List<CategoryPureVo> getList(List<Category> categories){
        return  categories.stream()
                .map(CategoryPureVo::new)
                .collect(Collectors.toList());
    }
}
