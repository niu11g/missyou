package com.lin.missyou.core;

import com.lin.missyou.exception.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionAdvice {

    //处理未知异常
    @ExceptionHandler(value=Exception.class)
    @ResponseBody
    //设置状态码
    @ResponseStatus(code= HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handleException(HttpServletRequest req,Exception ex){
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        UnifyResponse unify = new UnifyResponse(999,"服务器异常",method+" "+requestUrl);
        return unify;
//        System.out.println("hello");
    }

    //处理已知异常
    @ExceptionHandler(HttpException.class)
    public void httpException(HttpServletRequest req,HttpException ex){
        System.out.println("hello");
    }
}
