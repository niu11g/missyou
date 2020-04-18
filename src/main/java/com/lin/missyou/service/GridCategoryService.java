package com.lin.missyou.service;

import com.lin.missyou.model.GridCategory;
import com.lin.missyou.repository.GirdCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GridCategoryService {

    @Autowired
    private GirdCategoryRepository girdCategoryRepository;

    public List<GridCategory> findAll(){
        return girdCategoryRepository.findAll();
    }
}
