package com.lin.missyou.api.v1;

import com.lin.missyou.service.CategoryService;
import com.lin.missyou.service.GridCategoryService;
import com.lin.missyou.exception.http.NotFoundException;
import com.lin.missyou.model.Category;
import com.lin.missyou.model.GridCategory;
import com.lin.missyou.vo.CategoryAllVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GridCategoryService gridCategoryService;

    @GetMapping("/all")
    public CategoryAllVo getAll(){
        Map<Integer, List<Category>> categories = categoryService.getAll();
        CategoryAllVo categoryAllVo = new CategoryAllVo(categories);
        if(categoryAllVo == null){
            throw new NotFoundException(20002);
        }
        return categoryAllVo;
    }

    @GetMapping("/grid/all")
    public List<GridCategory> getGridCategoryList(){
        List<GridCategory> gridCategoryList = gridCategoryService.findAll();
        if(gridCategoryList.isEmpty()){
            throw new NotFoundException(20003);
        }
        return gridCategoryList;
    }
}
