package com.nguyenthotuan.mywebsitespring.domain.blog;

import com.nguyenthotuan.mywebsitespring.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Article {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;
    private String slug;
    private String title;
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    private String shortDescription;
    private boolean published = true;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User createdBy;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "article_category",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    public String getCreatedAt() {
        return createdAt.format(DATE_TIME_FORMATTER);
    }

    public String getUpdatedAt() {
        return updatedAt.format(DATE_TIME_FORMATTER);
    }

    public void setCategoryIds(List<Long> categories) {
        categories.forEach(category -> {
            if (this.categories.stream().noneMatch(c -> c.getId().equals(category))) {
                Category c = new Category();
                c.setId(category);
                this.categories.add(c);
            } else {
                this.categories.removeIf(c -> !c.getId().equals(category));
            }
        });
    }
}
