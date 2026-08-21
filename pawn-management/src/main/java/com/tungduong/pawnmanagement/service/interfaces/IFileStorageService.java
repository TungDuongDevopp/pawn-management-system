package com.tungduong.pawnmanagement.service.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileStorageService {
    String save(MultipartFile file,String directory) throws IOException;

    Resource get(String storageKey);

    void delete(String storageKey);

    boolean exists(String storageKey);
}
