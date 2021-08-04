package com.nguyenthotuan.mywebsitespring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlShortenDto {
    private String fullUrl;
    private String shortUrl;
}
