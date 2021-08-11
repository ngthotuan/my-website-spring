package com.nguyenthotuan.mywebsitespring;

import com.nguyenthotuan.mywebsitespring.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties
public class MyWebsiteSpringApplication {

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return args -> {
            storageService.init();

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(MyWebsiteSpringApplication.class, args);
    }

}
