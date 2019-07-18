package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.mapper.NewsMapper;
import com.how2java.tmall.pojo.News;
import com.how2java.tmall.service.NewsService;
import com.how2java.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 6/24/2019.
 */

@Controller
public class NewsController {
    @Autowired
    NewsService newsService;

    @Autowired
    NewsMapper newsMapper;

    @RequestMapping("admin_news_list")
    public String list(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<News> ns = newsService.list();
        int total = (int) new PageInfo<>(ns).getTotal();
        page.setTotal(total);
        model.addAttribute("ns", ns);
        model.addAttribute("page", page);
        return "admin/listNews";
    }
    @RequestMapping("admin_news_delete")
    public String delete(Model model, @RequestParam int id) {
        newsService.delete(id);
        return "redirect:admin_news_list";
    }
    @RequestMapping("admin_news_edit")
    public String edit(Model model, @RequestParam int id) {
        News n= newsService.get(id);
        model.addAttribute("n", n);
        return "admin/editNews";
    }
    @RequestMapping("admin_news_update")
    public String update(Model model,@RequestParam String title, @RequestParam String content, @RequestParam int id) {
        News n = newsService.get(id);
        n.setTitle(title);
        n.setContent(content);
        newsService.update(n);
        return "redirect:admin_news_list";
    }
    @RequestMapping("admin_news_add")
    public String add(Model model,@RequestParam String title, @RequestParam String content)
    {
        News n =new News();
        n.setTitle(title);
        n.setContent(content);
        n.setCreatedate(new Date());
        newsService.add(n);
        return "redirect:admin_news_list";
    }

}
