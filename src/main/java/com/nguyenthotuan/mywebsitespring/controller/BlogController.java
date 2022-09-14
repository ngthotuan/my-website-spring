package com.nguyenthotuan.mywebsitespring.controller;

import com.nguyenthotuan.mywebsitespring.domain.blog.Article;
import com.nguyenthotuan.mywebsitespring.domain.blog.Category;
import com.nguyenthotuan.mywebsitespring.service.ArticleService;
import com.nguyenthotuan.mywebsitespring.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("blog")
@RequiredArgsConstructor
public class BlogController {

    private final CategoryService categoryService;
    private final ArticleService articleService;

    @GetMapping
    public String getBlog(Model model) {
        List<Category> categories = categoryService.findAll();
        List<Article> articles = articleService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("articles", articles);
        return "blog/index";
    }

    @GetMapping("/{slug}")
    public String getBlogDetail(Model model, @PathVariable String slug) {
        Article article = articleService.findBySlug(slug);
        if (article == null) {
            return "redirect:/blog";
        }
        model.addAttribute("article", article);
        model.addAttribute("categories", categoryService.findAll());
        return "blog/detail";
    }
}
