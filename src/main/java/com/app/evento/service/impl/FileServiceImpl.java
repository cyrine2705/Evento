package com.app.evento.service.impl;

import com.app.evento.service.FileService;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
@Service
public class FileServiceImpl implements FileService {
    public String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("eventoapp2024.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        Credentials credentials = GoogleCredentials.fromStream(Files.newInputStream(Paths.get("credentials.json")));
        com.google.cloud.storage.Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format("https://firebasestorage.googleapis.com/v0/b/eventoapp2024.appspot.com/o/%s?alt=media", URLEncoder.encode(fileName, String.valueOf(StandardCharsets.UTF_8)));
    }


    public File convertToFile(MultipartFile multipartFile, String fileName) {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;    }


    public String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));    }

    @Override
    public String getFile(MultipartFile multipartFile) {

        try {
            if (multipartFile !=null) {

                if(multipartFile.getOriginalFilename()!=null) {
                    String fileName =   multipartFile.getOriginalFilename();
                    File file = this.convertToFile(multipartFile, fileName);
                    String TEMP_URL = this.uploadFile(file,fileName);

                    return TEMP_URL ;
                }
                else{
                    return "error";

                }
            }
            else{
                return "error";
            }


        } catch (Exception e) {
            e.printStackTrace();
            return  e.getMessage();
        }
    }
}
