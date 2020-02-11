package com.lin.missyou.Service;


import org.springframework.stereotype.Service;

@Service
public class BannerServiceImpl implements BannerService {

    public String getName(String name) {
        return "通过名字获取";

    }
}
