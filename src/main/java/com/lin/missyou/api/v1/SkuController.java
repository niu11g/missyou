package com.lin.missyou.api.v1;

import com.lin.missyou.model.Sku;
import com.lin.missyou.service.SkuService;
import com.lin.missyou.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: ly
 * @Date: 2020/10/6 11:34 上午
 * @Remark: sku api 接口
 */
@RestController
@RequestMapping("sku")
public class SkuController {
    @Autowired
    private SkuService skuService;

    @Autowired
    private SpuService spuService;

    public List<Sku> getSkuListInIds(@RequestParam(name = "ids",required = false)String ids){
        if(ids==null || ids.isEmpty()){
            return Collections.emptyList();
        }
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(s -> Long.parseLong(s.trim()))
                .collect(Collectors.toList());
        List<Sku> skus = skuService.getSkuListByIds(idList);
        return skus;
    }


}
