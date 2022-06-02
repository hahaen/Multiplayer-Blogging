package com.hahaen.multiplayerblogging.controller;

import com.hahaen.multiplayerblogging.entity.Result;
import com.hahaen.multiplayerblogging.entity.User;
import com.hahaen.multiplayerblogging.entity.loginResult;
import com.hahaen.multiplayerblogging.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

/**
 * @author hahaen
 * @date 2022/6/1 21:13
 */
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
    public Result auth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User loggedInUser = userService.getUserByUserName(authentication == null ? null : authentication.getName());

        if (loggedInUser == null) {
            return loginResult.success("用户没有登录", false);
        } else {
            return loginResult.success(null, loggedInUser, true);
        }
    }

    @GetMapping("/auth/logout")
    @ResponseBody
    public Result logout() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        User loggedInUser = userService.getUserByUserName(userName);

        if (loggedInUser == null) {
            return loginResult.failure("用户没有登录");
        } else {
            SecurityContextHolder.clearContext();
            return loginResult.success("success", false);
        }
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public Result register(@RequestBody Map<String, String> usernameAndPassword) {
        String username = usernameAndPassword.get("username");
        String password = usernameAndPassword.get("password");
        if (username == null || password == null) {
            return loginResult.failure("username/password == null");
        }
        if (username.length() < 1 || username.length() > 15) {
            return loginResult.failure("invalid username");
        }
        if (password.length() < 1 || password.length() > 15) {
            return loginResult.failure("invalid password");
        }

        try {
            userService.save(username, password);
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            return loginResult.failure("user already exists");
        }
        return loginResult.success("success", false);
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
            return loginResult.failure("用户不存在");
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        try {
            authenticationManager.authenticate(token);
            // 把用户信息保存在一个地方
            //   Cookie
            SecurityContextHolder.getContext().setAuthentication(token);

            return loginResult.success("登录成功", userService.getUserByUserName(username), true);
        } catch (BadCredentialsException e) {
            return loginResult.failure("密码不正确");
        }
    }

}
