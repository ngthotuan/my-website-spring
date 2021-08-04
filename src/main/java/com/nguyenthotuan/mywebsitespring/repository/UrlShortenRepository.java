package com.nguyenthotuan.mywebsitespring.repository;

import com.nguyenthotuan.mywebsitespring.domain.UrlShorten;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlShortenRepository extends JpaRepository<UrlShorten, Long> {
    UrlShorten findByShortUrl(String shortUrl);
}
