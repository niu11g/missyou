package com.lin.missyou.exception;

public class ServerErrorException extends  HttpException {
    public ServerErrorException(int code){
        this.httpStatusCode = 500;
        this.code = code;
    }
}
