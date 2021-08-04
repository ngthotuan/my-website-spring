package com.nguyenthotuan.mywebsitespring.repository;

import com.nguyenthotuan.mywebsitespring.domain.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {
    Optional<FileUpload> findBySaveName(String saveName);
}
