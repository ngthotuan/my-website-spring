package com.nguyenthotuan.mywebsitespring.repository;

import com.nguyenthotuan.mywebsitespring.domain.blog.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Article findBySlug(String slug);

    List<Article> findAllByPublished(boolean published);
}
