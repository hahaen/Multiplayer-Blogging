package com.hahaen.multiplayerblogging.service;

import com.hahaen.multiplayerblogging.dao.BlogDao;
import com.hahaen.multiplayerblogging.entity.Blog;
import com.hahaen.multiplayerblogging.entity.BlogResult;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @className: BlogService
 * @description: 博客Service
 * @author: hahaen
 * @date: 2022/6/2 13:16
 **/
@Service
public class BlogService {
    private BlogDao blogDao;
    private UserService userService;

    @Inject

    public BlogService(BlogDao blogDao,
                       UserService userService) {
        this.blogDao = blogDao;
        this.userService = userService;
    }


    public BlogResult getBlogs(Integer page, Integer pageSize, Integer userId) {
        try {
            List<Blog> blogs = blogDao.getBlogs(page, pageSize, userId);

            blogs.forEach(blog -> blog.setUser(userService.getUserById(blog.getUserId())));

            int cout = blogDao.count(userId);

            int pageCount = cout % pageSize == 0 ? cout / pageSize : cout / pageSize + 1;

            return BlogResult.newBlogs(blogs, cout, page, pageCount);
        } catch (Exception e) {
            e.printStackTrace();
            return BlogResult.failure("系统异常");
        }

    }
}
