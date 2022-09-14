package com.nguyenthotuan.mywebsitespring.repository;

import com.nguyenthotuan.mywebsitespring.domain.blog.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Article findBySlug(String slug);
}
