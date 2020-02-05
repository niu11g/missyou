package com.lin.missyou.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "myerror")
@PropertySource(value="classpath:config/exception-code.properties")
@Component
public class ExceptionCodeConfiguration {

    private Map<Integer,String> codes = new HashMap<Integer, String>();

    public Map<Integer, String> getCodes() {
        return codes;
    }

    public void setCodes(Map<Integer, String> codes) {
        this.codes = codes;
    }

    public String getMessage(int code){
        String message = codes.get(code);
        return message;
    }
}
