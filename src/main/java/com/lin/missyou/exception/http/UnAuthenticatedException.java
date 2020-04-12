package com.lin.missyou.exception.http;

public class UnAuthenticatedException extends HttpException {
    public UnAuthenticatedException(int code){
        this.httpStatusCode = 500;
        this.code = code;
    }
    //200-查询 201-创建 200-删除 200-更新
}
