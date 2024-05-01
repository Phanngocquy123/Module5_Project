package com.ra.project5.service.impl;

import com.ra.project5.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);
    private final Path root = Paths.get("uploads");

    public FileServiceImpl() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            LOGGER.error("[EXCEPTION] FileServiceImpl FileServiceImpl with ex {}", e);
        }
    }
    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
        } catch (Exception ex) {
            LOGGER.error("[EXCEPTION] FileServiceImpl save with ex {}", ex);
        }
    }

    @Override
    public Resource load(String fileName) {
        try {
            Path filePath = root.resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new Exception("File not found!");
            }
        } catch (Exception ex) {
            LOGGER.error("[EXCEPTION] FileServiceImpl load with ex {}", ex);
        }
        return null;

    }
}
