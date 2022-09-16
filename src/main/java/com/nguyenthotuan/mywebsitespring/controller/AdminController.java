package com.nguyenthotuan.mywebsitespring.controller;

import com.nguyenthotuan.mywebsitespring.domain.blog.Article;
import com.nguyenthotuan.mywebsitespring.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final ArticleService articleService;
    @GetMapping
    @ResponseBody
    public String index() {
        return "Admin route";
    }

    @GetMapping("/blog/edit/{id}")
    public String viewEditArticle(Model model, @PathVariable Long id) {
        Article article = articleService.findById(id);
        model.addAttribute("article", article);
        return "blog/edit";
    }

    @PostMapping(value="/blog/edit/{id}")
    public String editArticle(@PathVariable Long id,
                              @RequestParam String title,
                              @RequestParam String slug,
                              @RequestParam String content,
                              @RequestParam String shortDescription
                              ) {
        Article article = articleService.findById(id);
        article.setTitle(title);
        article.setSlug(slug);
        article.setContent(content);
        article.setShortDescription(shortDescription);
        articleService.save(article);
        return "redirect:/blog";
    }
}
