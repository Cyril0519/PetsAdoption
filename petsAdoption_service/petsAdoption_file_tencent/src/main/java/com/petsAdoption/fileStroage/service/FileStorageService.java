package com.petsAdoption.fileStroage.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String uploadFile(MultipartFile file);

    void deleteByKey(String key);
}
