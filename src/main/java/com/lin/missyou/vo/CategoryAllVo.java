package com.lin.missyou.vo;

import com.lin.missyou.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class CategoryAllVo {
    private List<CategoryPureVo> roots;
    private List<CategoryPureVo> subs;

    public CategoryAllVo(Map<Integer,List<Category>> map) {
        this.roots = map.get(1).stream()
                .map(CategoryPureVo::new)
                .collect(Collectors.toList());

        this.subs = map.get(2).stream()
                .map(CategoryPureVo::new)
                .collect(Collectors.toList());

//        this.roots = map.get(1);
//        this.subs = map.get(2);
    }
}
