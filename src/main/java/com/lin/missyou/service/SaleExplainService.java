/**
 * @作者 7七月
 * @微信公号 林间有风
 * @开源项目 $ http://7yue.pro
 * @免费专栏 $ http://course.7yue.pro
 * @我的课程 $ http://imooc.com/t/4294850
 * @创建时间 2019-08-17 04:57
 */
package com.lin.missyou.service;

import com.lin.missyou.model.SaleExplain;
import com.lin.missyou.repository.SaleExplainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleExplainService {
    @Autowired
    private SaleExplainRepository saleExplainRepository;

    public List<SaleExplain> getSaleExplainFixedList() {
        return this.saleExplainRepository.findByFixedOrderByIndex(true);
    }
}
