package com.nguyenthotuan.mywebsitespring.service;

import com.nguyenthotuan.mywebsitespring.domain.blog.Article;

import java.util.List;

public interface ArticleService {
    List<Article> findAll();
}
