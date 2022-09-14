package com.nguyenthotuan.mywebsitespring.service.impl;


import com.nguyenthotuan.mywebsitespring.domain.blog.Article;
import com.nguyenthotuan.mywebsitespring.repository.ArticleRepository;
import com.nguyenthotuan.mywebsitespring.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository repo;

    @Override
    public List<Article> findAll() {
        return repo.findAll();
    }

    @Override
    public Article findBySlug(String slug) {
        return repo.findBySlug(slug);
    }
}
