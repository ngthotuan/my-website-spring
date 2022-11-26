package com.nguyenthotuan.mywebsitespring.repository;

import com.nguyenthotuan.mywebsitespring.domain.blog.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Article findBySlug(String slug);

    Page<Article> findAllByPublished(boolean published, Pageable pageable);
    Page<Article> findAllByPublishedAndTitleContainsIgnoreCase(boolean published, String title, Pageable pageable);

    Page<Article> findByPublishedAndCategories_Slug(boolean published, String categorySlug, Pageable pageable);
}
