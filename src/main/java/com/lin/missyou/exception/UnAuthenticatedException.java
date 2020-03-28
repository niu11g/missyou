package com.lin.missyou.exception;

public class UnAuthenticatedException extends HttpException {
    public UnAuthenticatedException(int code){
        this.httpStatusCode = 500;
        this.code = code;
    }
}
