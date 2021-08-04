package com.nguyenthotuan.mywebsitespring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("blog")
public class BlogController {

    @GetMapping
    public String getBlog() {
        return "blog/index";
    }

    @GetMapping("{id}")
    public String getBlogDetail(Model model, @PathVariable String id) {
        model.addAttribute("id", id);
        return "blog/detail";
    }
}
