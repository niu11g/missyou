package com.lin.missyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.lin")
//默认扫描与启动类同级以及下级的类
public class MissyouApplication {
    public static void main(String[] args) {
        SpringApplication.run(MissyouApplication.class, args);
    }
    //小程序 强制更新
}
