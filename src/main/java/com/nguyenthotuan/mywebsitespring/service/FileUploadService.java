package com.nguyenthotuan.mywebsitespring.service;

import com.nguyenthotuan.mywebsitespring.domain.FileUpload;

import java.util.Optional;

public interface FileUploadService {
    <S extends FileUpload> S save(S s);

    Optional<FileUpload> findBySaveName(String saveName);
}
