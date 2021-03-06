package com.nguyenthotuan.mywebsitespring.controller;

import com.nguyenthotuan.mywebsitespring.config.AppProperties;
import com.nguyenthotuan.mywebsitespring.domain.FileUpload;
import com.nguyenthotuan.mywebsitespring.exception.StorageFileNotFoundException;
import com.nguyenthotuan.mywebsitespring.model.FileUploadDto;
import com.nguyenthotuan.mywebsitespring.service.FileUploadService;
import com.nguyenthotuan.mywebsitespring.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("upload")
@RequiredArgsConstructor
public class UploadController {
    private final StorageService storageService;
    private final FileUploadService fileUploadService;
    private final AppProperties appProperties;

    @GetMapping
    public String index() {
        return "upload/index";
    }

    @PostMapping
    public String index(Model model, FileUploadDto fileUploadDto) {
        //store file
        MultipartFile file = fileUploadDto.getFile();
        String fileName = file.getOriginalFilename();
        String storeName = storageService.getStorageFilename(file);
        storageService.store(fileUploadDto.getFile(), storeName);

        // save db
        FileUpload fileUpload = new FileUpload();
        fileUpload.setOriginName(fileName);
        fileUpload.setSaveName(storeName);
        fileUpload.setSize(file.getSize());
        fileUpload.setContentType(file.getContentType());
        fileUploadService.save(fileUpload);

        model.addAttribute("fileUrl", String.format("http://%s/upload/%s", appProperties.getHostName(), fileUpload.getSaveName()));
        return "upload/success";
    }

    @GetMapping("{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> getImage(@PathVariable String fileName, HttpServletRequest request) {
        Optional<FileUpload> opt = fileUploadService.findBySaveName(fileName);
        FileUpload fileUpload = opt.orElseThrow(() -> new StorageFileNotFoundException("T???p kh??ng t???n t???i"));

        Resource resource = storageService.loadResource(fileName);
        String contentType = request.getServletContext().getMimeType(resource.getFilename());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", fileUpload.getOriginName()))
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

}
