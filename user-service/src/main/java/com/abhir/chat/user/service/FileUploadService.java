package com.abhir.chat.user.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FileUploadService {

    public String generateMockPresignedUrl(String originalFileName) {
        String fakeFileId = UUID.randomUUID().toString();
        return "https://mock-s3.com/uploads/" + fakeFileId + "-" + originalFileName;
    }
}
