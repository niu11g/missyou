package com.lin.missyou.exception;

public class HttpException extends RuntimeException {
    protected  Integer code;
    protected  Integer httpStatusCode  = 500;
}
