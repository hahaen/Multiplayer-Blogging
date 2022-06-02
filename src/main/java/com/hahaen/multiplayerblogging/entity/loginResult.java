package com.hahaen.multiplayerblogging.entity;

/**
 * @className: loginResult
 * @description: 登录的Result
 * @author: hahaen
 * @date: 2022/6/2 15:50
 **/
public class loginResult extends Result<User> {
    Boolean isLogin;

    protected loginResult(String status, String msg, User user, boolean isLogin) {
        super(status, msg, user);
        this.isLogin = isLogin;
    }

    public static Result success(String msg, boolean isLogin) {
        return success(msg, null, isLogin);
    }

    public static Result success(String msg, User user, boolean isLogin) {
        return new loginResult("ok", msg, user, isLogin);
    }

    public static Result failure(String msg) {
        return new loginResult("fail", msg, null, false);
    }

    public Boolean getLogin() {
        return isLogin;
    }
}
