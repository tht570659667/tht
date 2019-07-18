package com.how2java.tmall.controller;

import java.util.List;

import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.UserService;
import com.how2java.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("admin_user_list")
    public String list(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<User> us = userService.list();
        int total = (int) new PageInfo<>(us).getTotal();
        page.setTotal(total);
        model.addAttribute("us", us);
        model.addAttribute("page", page);
        return "admin/listUser";
    }
    @RequestMapping("admin_user_delete")
    public String delete(Model model, @RequestParam int id) {
        userService.delete(id);
        return "redirect:admin_user_list";
    }
    @RequestMapping("admin_user_edit")
    public String edit(Model model, @RequestParam int id) {
        User user = userService.get(id);
        model.addAttribute("u", user);
        return "admin/editUser";
    }
    @RequestMapping("admin_user_update")
    public String update(Model model,@RequestParam String name, @RequestParam int id) {
        User user = userService.get(id);
        user.setName(name);
        userService.update(user);
        return "redirect:admin_user_list";
    }



}

