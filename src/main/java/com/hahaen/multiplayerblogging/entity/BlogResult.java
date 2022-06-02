package com.hahaen.multiplayerblogging.entity;

import java.util.List;

/**
 * @className: BlogResult
 * @description: Blog的Result
 * @author: hahaen
 * @date: 2022/6/2 15:56
 **/
public class BlogResult extends Result<List<Blog>> {
    private int total;
    private int page;
    private int totalPage;

    public static BlogResult newBlogs(List<Blog> data, int total, int page, int totalPage) {
        return new BlogResult("ok", "获取成功", data, total, page, totalPage);
    }

    private BlogResult(String status, String msg, List<Blog> data, int total, int page, int totalPage) {
        super(status, msg, data);
        this.total = total;
        this.page = page;
        this.totalPage = totalPage;
    }

    public static BlogResult failure(String msg) {
        return new BlogResult("fail", msg, null, 0, 0, 0);
    }

    public int getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPage() {
        return totalPage;
    }
}
