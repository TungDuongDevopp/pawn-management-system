package com.tungduong.pawnmanagement.service.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {
    String save(MultipartFile file);

    Resource get(String storageKey);

    void delete(String storageKey);

    boolean exists(String storageKey);
}
