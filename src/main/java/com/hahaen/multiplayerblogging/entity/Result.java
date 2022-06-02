package com.hahaen.multiplayerblogging.entity;

/**
 * @author hahaen
 * @date 2022/6/1 21:13
 */
public abstract class Result<T> {
    String status;
    String msg;
    T data;

//    public static Result failure(String message) {
//        return new Result("fail", message, false);
//    }
//
//    public static Result success(String message) {
//        return new Result("ok", message, false);
//    }

    protected Result(String status, String msg) {
        this(status, msg, null);
    }

    protected Result(String status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }
}
