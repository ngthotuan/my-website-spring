package com.nguyenthotuan.mywebsitespring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("admin")
public class AdminController {
    @GetMapping
    @ResponseBody
    public String index() {
        return "Admin route";
    }
}
