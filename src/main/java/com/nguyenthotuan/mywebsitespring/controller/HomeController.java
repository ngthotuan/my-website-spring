package com.nguyenthotuan.mywebsitespring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String getHomePage() {
        return "home/index";
    }

    @GetMapping("contact")
    public String getContact() {
        return "home/contact";
    }

    @GetMapping("login")
    public String getLogin() {
        return "home/login";
    }

    @GetMapping("register")
    public String getRegister() {
        return "home/register";
    }
}
