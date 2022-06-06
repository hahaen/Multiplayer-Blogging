package com.hahaen.multiplayerblogging.controller;

import com.hahaen.multiplayerblogging.entity.Result;
import com.hahaen.multiplayerblogging.entity.User;
import com.hahaen.multiplayerblogging.entity.loginResult;
import com.hahaen.multiplayerblogging.service.BlogService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

/**
 * @className: BlogController
 * @description: 博客Controller层
 * @author: hahaen
 * @date: 2022/6/6 11:20
 **/
@Controller
public class BlogController {
    private BlogService blogService;

    @Inject
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/blog")
    @ResponseBody
    public Result getBlogs(@RequestParam("page") Integer page,
                           @RequestParam(value = "userId", required = false) Integer userId) {
        if (page == null || page < 0) {
            page = 1;
        }
        return blogService.getBlogs(page, 10, userId);
    }
}
