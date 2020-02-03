package com.lin.missyou.sample.mysql;

import com.lin.missyou.sample.IConnection;

public class MySqlConnection implements IConnection {

    private String url;
    private Integer port;

    public MySqlConnection(String url,Integer port){
        this.url=url;
        this.port=port;
    }

    @Override
    public void getConnection() {
        System.out.println("mysql 链接地址："+url+"接口号:"+port);
    }
}
