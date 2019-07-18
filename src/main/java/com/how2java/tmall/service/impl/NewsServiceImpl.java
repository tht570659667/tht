package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.NewsMapper;
import com.how2java.tmall.pojo.News;
import com.how2java.tmall.pojo.NewsExample;
import com.how2java.tmall.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 6/24/2019.
 */

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    NewsMapper newsMapper;

    @Override
    public void add(News n) {
        newsMapper.insert(n);
    }

    @Override
    public void delete(int id) {
        newsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(News n) {
        newsMapper.updateByPrimaryKeySelective(n);
    }

    @Override
    public News get(int id) {
        return newsMapper.selectByPrimaryKey(id);
    }

    public List<News> list(){
        NewsExample example =new NewsExample();
        example.setOrderByClause("id desc");
        return newsMapper.selectByExample(example);
    }

    @Override
    public boolean isExist(String title) {
        NewsExample example =new NewsExample();
        example.createCriteria().andTitleEqualTo(title);
        List<News> result= newsMapper.selectByExample(example);
        if(!result.isEmpty())
            return true;
        return false;
    }
}
