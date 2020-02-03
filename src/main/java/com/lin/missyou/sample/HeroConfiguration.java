package com.lin.missyou.sample;

import com.lin.missyou.sample.hero.Camille;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//可以实例化时输入初始值
@Configuration
public class HeroConfiguration {
    @Bean
    public ISkill camille(){
        return new Camille("张三","女");
    }
}
