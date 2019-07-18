package com.how2java.tmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("")
public class PageController {
    //注册页面
    @RequestMapping("registerPage")
    public String registerPage() {
        return "fore/register";
    }
    //注册成功
    @RequestMapping("registerSuccessPage")
    public String registerSuccessPage() {
        return "fore/registerSuccess";
    }
    //登录页面
    @RequestMapping("loginPage")
    public String loginPage() {
        return "fore/login";
    }
    //支付 还未做。
    @RequestMapping("forealipay")
    public String alipay(){
        return "fore/alipay";
    }
}