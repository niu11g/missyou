package com.lin.missyou.sample;

import com.lin.missyou.sample.mysql.MySqlConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConConfiguration {

    @Value("${mysql.url}")
    private String url;
    @Value("${mysql.port}")
    private Integer port;

    @Bean
    public IConnection mysqlConnetction(){
        return new MySqlConnection(url,port);
    }



}
