package com.nguyenthotuan.mywebsitespring.service.impl;

import com.nguyenthotuan.mywebsitespring.domain.UrlShorten;
import com.nguyenthotuan.mywebsitespring.repository.UrlShortenRepository;
import com.nguyenthotuan.mywebsitespring.service.UrlShortenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UrlShortenServiceImpl implements UrlShortenService {
    private final UrlShortenRepository urlShortenRepository;

    private String randomShortUrl() {
        return UUID.randomUUID().toString().substring(0, 7);
    }

    @Override
    public UrlShorten findByShortUrl(String shortUrl) {
        return urlShortenRepository.findByShortUrl(shortUrl);
    }

    @Override
    public <S extends UrlShorten> S save(S s) {
        if (!s.getFullUrl().startsWith("http")) {
            s.setFullUrl(String.format("http://%s", s.getFullUrl()));
        }
        if (s.getShortUrl().isEmpty()) {
            s.setShortUrl(randomShortUrl());
        }
        if (s.getId() == null) {
            while (urlShortenRepository.findByShortUrl(s.getShortUrl()) != null) {
                s.setShortUrl(randomShortUrl());
            }
        }
        return urlShortenRepository.save(s);
    }
}
