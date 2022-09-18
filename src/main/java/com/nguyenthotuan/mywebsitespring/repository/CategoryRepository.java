package com.nguyenthotuan.mywebsitespring.repository;

import com.nguyenthotuan.mywebsitespring.domain.blog.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findBySlug(String slug);
}
