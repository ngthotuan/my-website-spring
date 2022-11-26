package com.nguyenthotuan.mywebsitespring.controller;

import com.nguyenthotuan.mywebsitespring.config.AppProperties;
import com.nguyenthotuan.mywebsitespring.domain.UrlShorten;
import com.nguyenthotuan.mywebsitespring.model.UrlShortenDto;
import com.nguyenthotuan.mywebsitespring.service.UrlShortenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("go")
@RequiredArgsConstructor
public class UrlShortenController {
    private final UrlShortenService urlShortenService;
    private final AppProperties appProperties;

    @GetMapping
    public String index() {
        return "url-shorten/index";
    }

    @PostMapping
    public String index(Model model, UrlShortenDto urlShortenDto) {
        UrlShorten urlShorten = new UrlShorten();
        BeanUtils.copyProperties(urlShortenDto, urlShorten);
        urlShortenService.save(urlShorten);
        model.addAttribute("fullUrl", String.format("%s://%s/go/%s", appProperties.getHostProtocol(),
                appProperties.getHostName(), urlShorten.getShortUrl()));
        return "url-shorten/success";
    }

    @GetMapping("{shortUrl}")
    public ModelAndView go(@PathVariable String shortUrl, ModelMap model) {
        UrlShorten urlShorten = urlShortenService.findByShortUrl(shortUrl);
        if (urlShorten == null) {
            model.addAttribute("message", "Liên kết không tồn tại hoặc đã bị gỡ bỏ!");
            return new ModelAndView("forward:/go", model);
        } else {
            long click = urlShorten.getClicks();
            click++;
            urlShorten.setClicks(click);
            urlShortenService.save(urlShorten);
            return new ModelAndView("redirect:" + urlShorten.getFullUrl());
        }
    }
}
