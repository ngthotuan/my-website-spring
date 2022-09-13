package com.nguyenthotuan.mywebsitespring.service;

import com.nguyenthotuan.mywebsitespring.domain.blog.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
}
