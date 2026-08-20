package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.helper.exception.FileStorageException;
import com.tungduong.pawnmanagement.helper.exception.ResourceNotFoundException;
import com.tungduong.pawnmanagement.service.interfaces.IFileStorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class LocalFileStorageService implements IFileStorageService {

    private final Path rootLocation;
    private static final List<String> allowedExtensions = Arrays.asList("pdf", "jpg", "jpeg", "png", "doc", "docx");
    private static final List<String> allowedMimeTypes = Arrays.asList(
            "application/pdf",
            "image/jpeg",
            "image/png",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );

    public LocalFileStorageService(@Value("${document.upload-file.base-uri}") String storageLocation) {
        this.rootLocation = Paths.get(URI.create(storageLocation)).normalize();

        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new FileStorageException("Could not initialize storage");
        }
    }

    private String createStorageKey(MultipartFile file) {
        if(Objects.isNull(file) || file.isEmpty()) {
            throw new FileStorageException("File is empty");
        }
        String fileName = FilenameUtils.getName(file.getOriginalFilename());
        String contentType = file.getContentType();
        if(!allowedMimeTypes.contains(contentType)) {
            throw new FileStorageException("File type is not allowed: " + contentType);
        }
        String extension = FilenameUtils.getExtension(fileName);
        if(!allowedExtensions.contains(extension)) {
            throw new FileStorageException("File extension is not allowed: " + extension);
        }
        return "%s.%s".formatted(UUID.randomUUID(), extension);

    }

    @Override
    public String save(MultipartFile file) {

        String storageKey = createStorageKey(file);
        try {
            Path target = rootLocation.resolve(storageKey).normalize();

            file.transferTo(target);

            return storageKey;

        } catch (IOException e) {
            throw new FileStorageException("Could not save file");
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
            throw new FileStorageException("Could not read file");
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
            throw new FileStorageException("Could not delete file");
        }
    }

    @Override
    public boolean exists(String storageKey) {
        Path file = rootLocation.resolve(storageKey).normalize();
        return Files.exists(file);
    }
}