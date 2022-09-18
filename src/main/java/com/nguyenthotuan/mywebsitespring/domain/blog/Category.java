package com.nguyenthotuan.mywebsitespring.domain.blog;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String slug;
    private String name;

    @ManyToMany(mappedBy = "categories")
    @ToString.Exclude
    private List<Article> articles;
}
