package com.nguyenthotuan.mywebsitespring.controller;

import com.nguyenthotuan.mywebsitespring.domain.blog.Article;
import com.nguyenthotuan.mywebsitespring.domain.blog.Category;
import com.nguyenthotuan.mywebsitespring.service.ArticleService;
import com.nguyenthotuan.mywebsitespring.service.CategoryService;
import com.nguyenthotuan.mywebsitespring.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import static com.nguyenthotuan.mywebsitespring.controller.BlogController.STATIC_FILE_PATH;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final StorageService storageService;
    @GetMapping
    @ResponseBody
    public String index() {
        return "Admin route";
    }

    @GetMapping("/blog/edit")
    public String viewEditArticle(Model model) {
        Article article = new Article();
        return doViewEditArticle(model, article);
    }

    @GetMapping("/blog/edit/{id}")
    public String viewEditArticle(Model model, @PathVariable(required = false) Long id) {
        Article article = articleService.findById(id);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("article", article);
        model.addAttribute("categories", categories);
        return "blog/edit";
    }

    private String doViewEditArticle(Model model, Article article) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("article", article);
        model.addAttribute("categories", categories);
        return "blog/edit";
    }

    @PostMapping(value="/blog/edit")
    public String editArticle(@RequestParam String title,
                              @RequestParam String slug,
                              @RequestParam String content,
                              @RequestParam String shortDescription,
                              @RequestParam List<Long> categories,
                              @RequestParam(required = false, defaultValue = "false") boolean published,
                              @RequestPart(required = false) MultipartFile image
                              ) {
        Article article = new Article();
        article.setTitle(title);
        article.setSlug(slug);
        article.setContent(content);
        article.setShortDescription(shortDescription);
        article.setCategoryIds(categories);
        article.setPublished(published);
        String imageFileName = storageService.getStorageFilename(image);
        storageService.store(image, STATIC_FILE_PATH, imageFileName);
        article.setImage(imageFileName);
        articleService.save(article);
        return "redirect:/blog";
    }

    @PostMapping(value="/blog/edit/{id}")
    public String editArticle(@PathVariable Long id,
                              @RequestParam String title,
                              @RequestParam String slug,
                              @RequestParam String content,
                              @RequestParam String shortDescription,
                              @RequestParam List<Long> categories,
                              @RequestParam(required = false, defaultValue = "false") boolean published,
                              @RequestPart(required = false) MultipartFile image
                              ) {
        Article article = articleService.findById(id);
        article.setTitle(title);
        article.setSlug(slug);
        article.setContent(content);
        article.setShortDescription(shortDescription);
        article.setCategoryIds(categories);
        article.setPublished(published);
        if (image != null && !image.isEmpty()) {
            String imageFileName = storageService.getStorageFilename(image);
            storageService.store(image, STATIC_FILE_PATH, imageFileName);
            try {
                storageService.delete(STATIC_FILE_PATH + "/" + article.getImage());
            } catch (Exception ignored) {
            }
            article.setImage(imageFileName);
        }
        article.setUpdatedAt(LocalDateTime.now());
        articleService.save(article);
        return "redirect:/blog";
    }
}
