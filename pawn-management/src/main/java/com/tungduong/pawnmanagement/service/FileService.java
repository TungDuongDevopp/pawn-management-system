package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.helper.exception.FileStorageException;
import com.tungduong.pawnmanagement.helper.exception.InvalidTypeFileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {
    private static final List<String> allowedExtensions = Arrays.asList("pdf", "jpg", "jpeg", "png", "doc", "docx");
    private static final List<String> allowedMimeTypes = Arrays.asList(
            "application/pdf",
            "image/jpeg",
            "image/png",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );
    @Value("${document.upload-file.base-uri}")
    private String baseURI;


    public String store(MultipartFile file, String folder)
            throws IOException {

        if (file.isEmpty()) {
            throw new FileStorageException("File is empty, please upload again!");
        }

        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null || originalFileName.isBlank()) {
            throw new FileStorageException("Invalid file name!");
        }

        String contentType = file.getContentType();

        if (contentType == null || !allowedMimeTypes.contains(contentType)) {
            throw new InvalidTypeFileException(
                    "Invalid file type, please upload again!"
            );
        }

        boolean validExtension = allowedExtensions.stream()
                .anyMatch(ext ->
                        originalFileName.toLowerCase()
                                .endsWith("." + ext)
                );

        if (!validExtension) {
            throw new InvalidTypeFileException(
                    "Invalid file extension, please upload again!"
            );
        }

        String extension = originalFileName.substring(
                originalFileName.lastIndexOf(".")
        );

        String finalName = UUID.randomUUID() + extension;

        Path folderPath = Paths.get(URI.create(baseURI))
                .resolve(folder);

        Files.createDirectories(folderPath);

        Path targetPath = folderPath.resolve(finalName);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, targetPath);
        }

        return finalName;
    }
    public long getFileLength(String fileName,String folder) throws IOException {
        Path path = Paths.get(URI.create(baseURI)).resolve(folder).resolve(fileName);
        if (!Files.isRegularFile(path)) {
            throw new FileStorageException(
                    "File does not exist, please upload again!"
            );
        }
        return Files.size(path);
    }
    public InputStreamResource getFile(String fileName, String folder)
            throws IOException {

        Path path = Paths.get(URI.create(baseURI))
                .resolve(folder)
                .resolve(fileName);

        if (!Files.isRegularFile(path)) {
            throw new FileStorageException(
                    "File does not exist, please upload again!"
            );
        }

        return new InputStreamResource(Files.newInputStream(path));
    }
}

