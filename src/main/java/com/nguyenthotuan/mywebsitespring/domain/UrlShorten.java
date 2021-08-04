package com.nguyenthotuan.mywebsitespring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "URL_SHORTEN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlShorten {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullUrl;
    private String shortUrl;
    private long clicks = 0;
}
