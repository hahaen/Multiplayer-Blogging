package com.hahaen.multiplayerblogging.service;


import com.hahaen.multiplayerblogging.dao.BlogDao;
import com.hahaen.multiplayerblogging.entity.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

/**
 * @className: BlogServiceTest
 * @description: 博客Service测试
 * @author: hahaen
 * @date: 2022/6/2 13:15
 **/
@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {
    @Mock
    BlogDao blogDao;
    @InjectMocks
    BlogService blogService;

    @Test
    public void getBlogsFromDb() {
        blogService.getBlogs(1, 10, null);
        verify(blogDao).getBlogs(1, 10, null);
    }

    @Test
    void returnFailureWhenExceptionThrown() {
        when(blogDao.getBlogs(anyInt(), anyInt(), any())).thenThrow(new RuntimeException());

        Result result = blogService.getBlogs(1, 10, null);
        Assertions.assertEquals("fail", result.getStatus());
        Assertions.assertEquals("系统异常", result.getMsg());

    }
}
