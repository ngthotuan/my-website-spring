package com.nguyenthotuan.mywebsitespring.controller;

import com.nguyenthotuan.mywebsitespring.domain.blog.Article;
import com.nguyenthotuan.mywebsitespring.model.ArticleDto;
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
    public String editArticle(@PathVariable Long id, @RequestBody ArticleDto articleDto) {
        Article article = articleService.findById(id);
        article.setTitle(articleDto.getTitle());
        article.setSlug(articleDto.getSlug());
        article.setContent(articleDto.getContent());
        article.setShortDescription(articleDto.getShortDescription());
        articleService.save(article);
        return "redirect:/blog";
    }
}
