package com.nguyenthotuan.mywebsitespring.service.impl;

import com.nguyenthotuan.mywebsitespring.config.AppProperties;
import com.nguyenthotuan.mywebsitespring.exception.StorageException;
import com.nguyenthotuan.mywebsitespring.exception.StorageFileNotFoundException;
import com.nguyenthotuan.mywebsitespring.service.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Component
public class FileSystemStorageServiceImpl implements StorageService {
    private final Path rootLocation;

    public FileSystemStorageServiceImpl(AppProperties properties) {
        this.rootLocation = Paths.get(properties.getUploadLocation());
    }

    private Optional<String> getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    @Override
    public String getStorageFilename(MultipartFile file) {
        String originName = file.getOriginalFilename();
        if (originName != null) {
            int i = originName.lastIndexOf(".");
            return String.format("%s-%d.%s", originName.substring(0, i), System.currentTimeMillis(), originName.substring(i + 1));
        }
        throw new StorageException("Failed to get store of empty file");
    }

    @Override
    public void store(MultipartFile file, String fileName) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file");
        }
        Path destinationFile = this.rootLocation.resolve(Paths.get(fileName))
                .normalize().toAbsolutePath();
        if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
            throw new StorageException("Can't store file outside current directory");
        }
        try {
            file.transferTo(destinationFile);
        } catch (IOException e) {
            throw new StorageException("Fail to store file", e);
        }
    }

    @Override
    public Resource loadResource(String fileName) {
        Path file = rootLocation.resolve(fileName);
        try {
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            }
        } catch (MalformedURLException ignored) {
        }

        throw new StorageFileNotFoundException("Could not read file " + fileName);
    }

    @Override
    public void delete(String fileName) {
        Path destinationFile = rootLocation.resolve(fileName)
                .normalize().toAbsolutePath();
        try {
            Files.delete(destinationFile);
        } catch (NoSuchFileException e) {
            throw new StorageFileNotFoundException(String.format("File %s not found", fileName));
        } catch (IOException e) {
            throw new StorageException("Could not delete file: " + fileName);
        }
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not create store directory: " + rootLocation);
        }
    }
}
