package com.lin.missyou.exception;

public class ParameterException extends  HttpException {
    public ParameterException(int code){
        this.httpStatusCode = 400;
        this.code = code;
    }
}
