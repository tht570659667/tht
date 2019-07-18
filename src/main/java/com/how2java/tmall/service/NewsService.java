package com.how2java.tmall.service;

import com.how2java.tmall.pojo.News;

import java.util.List;


/**
 * Created by Administrator on 6/24/2019.
 */
public interface NewsService {
    void add(News n);
    void delete(int id);
    void update(News n);
    News get(int id);
    List list();
    boolean isExist(String name);
}
