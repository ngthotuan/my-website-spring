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
}
