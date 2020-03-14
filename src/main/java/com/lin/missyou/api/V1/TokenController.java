package com.lin.missyou.api.V1;


import com.lin.missyou.core.enumeration.LoginType;
import com.lin.missyou.dto.TokenGetDTO;
import com.lin.missyou.exception.NotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "token")
public class TokenController {

    @PostMapping("")
    public Map<String,String> getToken(@RequestBody @Validated TokenGetDTO userData){
        Map<String,String> map = new HashMap<>();
        String token = null;
//        LoginType.USER_WX.test();
        switch (userData.getType()){
            case USER_WX:
                break;
            case USER_EMAIL:
                break;
            default:
                throw new NotFoundException(10003);
        }
        return null;

    }
}
