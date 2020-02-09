package com.lin.missyou.core;

import com.lin.missyou.core.configuration.ExceptionCodeConfiguration;
import com.lin.missyou.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionAdvice {

    @Autowired
    private ExceptionCodeConfiguration exceptionCodeConfiguration;

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
    public ResponseEntity<UnifyResponse> httpException(HttpServletRequest req, HttpException e){
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        UnifyResponse message = new UnifyResponse(e.getCode(),exceptionCodeConfiguration.getMessage(e.getCode()),method+" "+requestUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());
        ResponseEntity<UnifyResponse> r = new ResponseEntity<>(message,headers,httpStatus);
        return r;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(code=HttpStatus.BAD_REQUEST)
    public UnifyResponse handleBeanValidation(HttpServletRequest req,MethodArgumentNotValidException e){
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = this.formatAllErrorMessages(errors);
        return new UnifyResponse(10001,message,method + " " + requestUrl);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code=HttpStatus.BAD_REQUEST)
    @ResponseBody
    public UnifyResponse handleConstraintException(HttpServletRequest req, ConstraintViolationException e){
        e.getConstraintViolations();
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        String message = e.getMessage();

        return new UnifyResponse(100001,message,method + " " + requestUrl);
//        for (ConstraintViolation error: e.getConstraintViolations()
//             ) {
//            ConstraintViolation a = error;
//        }
    }

    private String formatAllErrorMessages(List<ObjectError> errors){
        StringBuffer errorMsg = new StringBuffer();
        errors.forEach(error ->
                errorMsg.append(error.getDefaultMessage()).append(';'));
        return errorMsg.toString();
    }
}
