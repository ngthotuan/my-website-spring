package com.nguyenthotuan.mywebsitespring.service;

import com.nguyenthotuan.mywebsitespring.domain.UrlShorten;
import org.springframework.stereotype.Service;

@Service
public interface UrlShortenService {
    UrlShorten findByShortUrl(String shortUrl);

    <S extends UrlShorten> S save(S s);
}
