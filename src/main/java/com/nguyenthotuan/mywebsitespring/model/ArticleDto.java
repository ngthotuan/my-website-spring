package com.nguyenthotuan.mywebsitespring.model;

import lombok.Data;

/**
 * Created by tuannt7 on 14/09/2022
 */

@Data
public class ArticleDto {
    private String slug;
    private String title;
    private String content;
    private String shortDescription;
}
