package com.nguyenthotuan.mywebsitespring.service.impl;


import com.nguyenthotuan.mywebsitespring.domain.blog.Article;
import com.nguyenthotuan.mywebsitespring.domain.blog.Category;
import com.nguyenthotuan.mywebsitespring.repository.CategoryRepository;
import com.nguyenthotuan.mywebsitespring.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repo;

    @Override
    public List<Category> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Article> findAllByCategory(String categorySlug) {
        Category category = repo.findBySlug(categorySlug);
        if (category == null) {
            return Collections.emptyList();
        }
        return category.getArticles();
    }

    @Override
    public List<Article> findPublishedByCategory(String categorySlug) {
        Category category = repo.findBySlug(categorySlug);
        if (category == null) {
            return Collections.emptyList();
        }
        return category.getArticles().stream()
                .filter(Article::isPublished)
                .collect(Collectors.toList());
    }
}
