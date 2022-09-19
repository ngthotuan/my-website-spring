package com.nguyenthotuan.mywebsitespring.service.impl;


import com.nguyenthotuan.mywebsitespring.domain.blog.Article;
import com.nguyenthotuan.mywebsitespring.repository.ArticleRepository;
import com.nguyenthotuan.mywebsitespring.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository repo;

    @Override
    public Page<Article> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Page<Article> findAllPublished(Pageable pageable) {
        return repo.findAllByPublished(true, pageable);
    }

    @Override
    public Page<Article> findAllPublishedByCategory(String categorySlug, Pageable pageable) {
        return repo.findByPublishedAndCategories_Slug(true, categorySlug, pageable);
    }

    @Override
    public Article findBySlug(String slug) {
        return repo.findBySlug(slug);
    }

    @Override
    public Article findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void save(Article article) {
        repo.save(article);
    }
}
