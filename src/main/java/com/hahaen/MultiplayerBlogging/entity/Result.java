package com.hahaen.MultiplayerBlogging.entity;

public class Result {
    String status;
    String msg;
    Boolean isLogin;
    Object data;

    public static Result failure(String message) {
        return new Result("fail", message, false);
    }

    public static Result Success(String message, Boolean isLogin) {
        return new Result("ok", message, isLogin);
    }

    public static Result Success(String message, Boolean isLogin, User loggedInUser) {
        return new Result("ok", message, isLogin, loggedInUser);
    }

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
