package com.lin.missyou.api.V1;

import com.lin.missyou.Service.BannerService;
import com.lin.missyou.sample.hero.Diana;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/banner")
public class BannerController {
    // 编译项目
    // 重启服务器
    @Autowired
    private Diana diana;

    @Autowired
    private BannerService bannerService;

    @GetMapping("/test")
    public String test(){
        diana.r();
        return "Hello,九十月";
    }

}
