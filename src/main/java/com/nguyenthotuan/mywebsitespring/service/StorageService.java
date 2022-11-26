package com.nguyenthotuan.mywebsitespring.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface StorageService {

    String getStorageFilename(MultipartFile file);

    void store(MultipartFile file, String fileName);

    void store(MultipartFile file, String relativePath, String fileName);

    Resource loadResource(String fileName);

    void delete(String fileName);

    void init();
}
