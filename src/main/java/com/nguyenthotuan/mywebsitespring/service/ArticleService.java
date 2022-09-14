package com.nguyenthotuan.mywebsitespring.service;

import com.nguyenthotuan.mywebsitespring.domain.blog.Article;

import java.util.List;

public interface ArticleService {
    List<Article> findAll();

    Article findBySlug(String slug);

    Article findById(Long id);

    void save(Article article);
}
