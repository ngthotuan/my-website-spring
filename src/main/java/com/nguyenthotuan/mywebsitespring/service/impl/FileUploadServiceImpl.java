package com.nguyenthotuan.mywebsitespring.service.impl;

import com.nguyenthotuan.mywebsitespring.domain.FileUpload;
import com.nguyenthotuan.mywebsitespring.repository.FileUploadRepository;
import com.nguyenthotuan.mywebsitespring.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    private final FileUploadRepository fileUploadRepository;

    @Override
    public <S extends FileUpload> S save(S s) {
        return fileUploadRepository.save(s);
    }

    @Override
    public Optional<FileUpload> findBySaveName(String saveName) {
        return fileUploadRepository.findBySaveName(saveName);
    }
}
