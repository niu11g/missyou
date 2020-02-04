package com.lin.missyou.sample;

import com.lin.missyou.sample.condition.DianaConditional;
import com.lin.missyou.sample.condition.IreliaConditional;
import com.lin.missyou.sample.hero.Camille;
import com.lin.missyou.sample.hero.Diana;
import com.lin.missyou.sample.hero.Irelia;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

//可以实例化时输入初始值
@Configuration
public class HeroConfiguration {

//    @Bean
//    public ISkill camille(){
//        return new Camille("张三","女");
//    }

    @Bean
    @ConditionalOnProperty(value = "hero.condition",havingValue = "irelia",matchIfMissing = true)
//    @ConditionalOnBean(name="mysqlConnetction")
//    @ConditionalOnMissingBean(name="mysqlConnetction")
//  @Conditional(IreliaConditional.class)
    public ISkill irelia(){
        return new Irelia();
    }

    @Bean
    @ConditionalOnProperty(value = "hero.condition",havingValue = "diana")
//    @Conditional(DianaConditional.class)
    public ISkill diana(){
        return new Diana();
    }
}
