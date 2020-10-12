/**
 * @作者 7七月
 * @微信公号 林间有风
 * @开源项目 $ http://7yue.pro
 * @免费专栏 $ http://course.7yue.pro
 * @我的课程 $ http://imooc.com/t/4294850
 * @创建时间 2019-08-17 05:03
 */
package com.lin.missyou.api.v1;

import com.lin.missyou.exception.http.NotFoundException;
import com.lin.missyou.model.SaleExplain;
import com.lin.missyou.service.SaleExplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/sale_explain")
public class SaleExplainController {

    @Autowired
    private SaleExplainService saleExplainService;


    @GetMapping("/fixed")
    public List<SaleExplain> getFixed() {
        List<SaleExplain> saleExplains = saleExplainService.getSaleExplainFixedList();
        if (saleExplains.isEmpty()) {
            throw new NotFoundException(30011);
        }
        return saleExplains;
    }
}