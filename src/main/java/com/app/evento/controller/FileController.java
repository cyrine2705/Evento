package com.app.evento.controller;

import com.app.evento.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    @PostMapping("/UploadFile")
    public   String uploadFile(@RequestParam(value = "file") MultipartFile multipartFile){
        return fileService.getFile(multipartFile);
    }
}
