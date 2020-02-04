package com.lin.missyou;

import com.lin.missyou.sample.EnableLOLConfiguration;
import com.lin.missyou.sample.HeroConfiguration;
import com.lin.missyou.sample.ISkill;
import com.lin.missyou.sample.LOLConfigurationSelector;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

//默认扫描与启动类同级以及下级的类
//@Import(HeroConfiguration.class)
//@Import(LOLConfigurationSelector.class)
@EnableLOLConfiguration
public class TestApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(TestApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        ISkill iSkill = (ISkill) context.getBean("diana");
        iSkill.r();

    }
    //小程序 强制更新
}
