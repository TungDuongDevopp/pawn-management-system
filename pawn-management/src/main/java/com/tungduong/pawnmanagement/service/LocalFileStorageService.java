package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.helper.exception.FileStorageException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.service.interfaces.IFileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class LocalFileStorageService implements IFileStorageService {

    private final Path rootLocation;

    public LocalFileStorageService(@Value("${document.upload-file.base-uri}") String storageLocation) {
        this.rootLocation = Paths.get(URI.create(storageLocation)).normalize();

        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    @Override
    public String save(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileStorageException("File is empty");
        }

        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        String extension = "";

        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex >= 0) {
            extension = originalFileName.substring(dotIndex);
        }

        String storageKey = UUID.randomUUID() + extension;

        try {
            Path target = rootLocation.resolve(storageKey).normalize();

            file.transferTo(target);

            return storageKey;

        } catch (IOException e) {
            throw new RuntimeException("Could not save file", e);
        }
    }

    @Override
    public Resource get(String storageKey) {
        try {
            Path file = rootLocation.resolve(storageKey).normalize();

            Resource resource = new UrlResource(file.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new ResourceNotFoundException("File not found");
            }

            return resource;

        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file", e);
        }
    }

    @Override
    public void delete(String storageKey) {
        try {
            Path file = rootLocation.resolve(storageKey).normalize();
            if(!Files.exists(file)){
                throw new ResourceNotFoundException("File not found");
            }

            Files.deleteIfExists(file);

        } catch (IOException e) {
            throw new RuntimeException("Could not delete file", e);
        }
    }

    @Override
    public boolean exists(String storageKey) {
        Path file = rootLocation.resolve(storageKey).normalize();
        return Files.exists(file);
    }
}