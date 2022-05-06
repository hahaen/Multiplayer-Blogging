package com.hahaen.MultiplayerBlogging.entity;

public class Result {
    String status;
    String msg;
    Boolean isLogin;
    Object data;


    public Result(String status, String msg, Boolean isLogin) {
        this(status, msg, isLogin, null);
    }

    public Result(String status, String msg, Boolean isLogin, Object data) {
        this.status = status;
        this.msg = msg;
        this.isLogin = isLogin;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public Boolean getLogin() {
        return isLogin;
    }

    public Object getData() {
        return data;
    }
}
