package com.nguyenthotuan.mywebsitespring.controller;

import com.nguyenthotuan.mywebsitespring.domain.blog.Article;
import com.nguyenthotuan.mywebsitespring.domain.blog.Category;
import com.nguyenthotuan.mywebsitespring.service.ArticleService;
import com.nguyenthotuan.mywebsitespring.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("blog")
@RequiredArgsConstructor
public class BlogController {

    private final CategoryService categoryService;
    private final ArticleService articleService;

    @GetMapping
    public String getBlogs(Model model,
                           @RequestParam(required = false) String category,
                           @RequestParam(required = false, defaultValue = "1") int page,
                           @RequestParam(required = false, defaultValue = "4") int size) {
        List<Category> categories = categoryService.findAll();
        if (page < 1) {
            page = 1;
        }
        if (size < 1) {
            size = 4;
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Article> articlePage;
        if (category != null) {
            articlePage = articleService.findAllPublishedByCategory(category, pageable);
        } else {
            articlePage = articleService.findAllPublished(pageable);
        }
        model.addAttribute("categories", categories);
        model.addAttribute("articles", articlePage.getContent());
        model.addAttribute("category", category);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", articlePage.getTotalPages());
        return "blog/index";
    }

    @GetMapping("/{slug}")
    public String getBlogDetail(Model model, @PathVariable String slug) {
        Article article = articleService.findBySlug(slug);
        if (article == null || !article.isPublished()) {
            return "redirect:/blog";
        }
        model.addAttribute("article", article);
        model.addAttribute("categories", categoryService.findAll());
        return "blog/detail";
    }
}
