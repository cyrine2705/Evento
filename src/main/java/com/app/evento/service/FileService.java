package com.app.evento.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
     String getFile(MultipartFile multipartFile);
}
