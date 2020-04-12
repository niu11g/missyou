package com.lin.missyou.api.V1;


import com.lin.missyou.Service.WxAuthenticationService;
import com.lin.missyou.dto.TokenDTO;
import com.lin.missyou.dto.TokenGetDTO;
import com.lin.missyou.exception.http.NotFoundException;
import com.lin.missyou.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private WxAuthenticationService wxAuthenticationService;

    @PostMapping("")
    public Map<String,String> getToken(@RequestBody @Validated TokenGetDTO userData){
        Map<String,String> map = new HashMap<>();
        String token = null;
//        LoginType.USER_WX.test();
        switch (userData.getType()){
            case USER_WX:
                token = wxAuthenticationService.code2Session(userData.getAccount());
                break;
            case USER_EMAIL:
                break;
            default:
                throw new NotFoundException(10003);
        }
        map.put("token", token);
        return map;
    }
    @PostMapping("/verify")
    public Map<String,Boolean> verify(@RequestBody TokenDTO token){
        Map<String,Boolean> map = new HashMap<>();
        Boolean valid = JwtToken.verifyToken(token.getToken());
        map.put("is_valid",valid);
        return map;
    }
}
