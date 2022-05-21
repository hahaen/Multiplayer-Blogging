package com.hahaen.MultiplayerBlogging.controller;

import com.hahaen.MultiplayerBlogging.entity.Result;
import com.hahaen.MultiplayerBlogging.entity.User;
import com.hahaen.MultiplayerBlogging.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Map;

@Controller
public class AuthController {
    private UserService userService;
    private AuthenticationManager authenticationManager;

    @Inject
    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/auth")
    @ResponseBody
    public Object auth() {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        User loggedInUser = userService.getUserByUserName(userName);

        if (loggedInUser == null) {
            return new Result("ok", "用户未登录", false);
        } else {
            return new Result("ok", null, true, loggedInUser);
        }
    }

    @GetMapping("/auth/logout")
    @ResponseBody
    public Object logout() {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        User loggedInUser = userService.getUserByUserName(userName);

        if (loggedInUser == null) {
            return new Result("fail", "用户未登录", false);
        } else {
            SecurityContextHolder.clearContext();
            return new Result("ok", "注销成功", false);
        }
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public Result register(@RequestBody Map<String, String> usernameAndPassword) {
        String username = usernameAndPassword.get("username");
        String password = usernameAndPassword.get("password");

        if (username == null || password == null) {
            return new Result("fail", "username/password == null", false);
        }
        if (username.length() < 1 || username.length() > 15) {
            return new Result("fail", "无效 username", false);
        }
        if (password.length() < 1 || password.length() > 15) {
            return new Result("fail", "无效 password", false);
        }

        User user = userService.getUserByUserName(username);
        if (user == null) {
            userService.save(username, password);
            return new Result("ok", "success!", false);
        } else {
            return new Result("fail", "用户已经存在", false);
        }

    }

    @PostMapping("/auth/login")
    @ResponseBody
    public Result login(@RequestBody Map<String, Object> usernameAndPassword) {
        String username = usernameAndPassword.get("username").toString();
        String password = usernameAndPassword.get("password").toString();

        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return new Result("fail", "用户不存在", false);
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password);

        try {
            authenticationManager.authenticate(token);
            // 把用户信息保存在一个地方
            // cookie
            SecurityContextHolder.getContext().setAuthentication(token);

            return new Result("ok", "登录成功", true,
                    userService.getUserByUserName(username));
        } catch (BadCredentialsException e) {
            return new Result("fail", "密码不正确", false);
        }
    }

}
