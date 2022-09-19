package com.nguyenthotuan.mywebsitespring.service.impl;


import com.nguyenthotuan.mywebsitespring.domain.blog.Category;
import com.nguyenthotuan.mywebsitespring.repository.CategoryRepository;
import com.nguyenthotuan.mywebsitespring.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repo;

    @Override
    public List<Category> findAll() {
        return repo.findAll();
    }

//    @Override
//    public Page<Article> findAllByCategory(String categorySlug, Pageable pageable) {
//        Category category = repo.findBySlug(categorySlug);
//
//    }
//
//    @Override
//    public Page<Article> findPublishedByCategory(String categorySlug, Pageable pageable) {
//        Category category = repo.findBySlug(categorySlug);
//        if (category == null) {
//            return Collections.emptyList();
//        }
//        return category.getArticles().stream()
//                .filter(Article::isPublished)
//                .collect(Collectors.toList());
//    }
}
