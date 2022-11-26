package com.nguyenthotuan.mywebsitespring.service;

import com.nguyenthotuan.mywebsitespring.domain.blog.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    Page<Article> findAll(Pageable pageable);

    Page<Article> findAllPublished(Pageable pageable);

    Page<Article> findAllPublished(Pageable pageable, String search);
    Page<Article> findAllPublishedByCategory(String categorySlug, Pageable pageable);

    Article findBySlug(String slug);

    Article findById(Long id);

    void save(Article article);
}
