package fr.picom.picomspring.service.impl;

import fr.picom.picomspring.exceptions.FileUploadException;
import fr.picom.picomspring.service.FilesStorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    private final Path root = Paths.get("src", "main", "resources", "static", "images");

    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void save(MultipartFile file, String newFilename) {
        try {
            // Obtenir un nouveau Path avec le nouveau nom de fichier
            Path newFilePath = this.root.resolve(newFilename);
            Files.copy(file.getInputStream(), newFilePath);
        } catch (FileAlreadyExistsException e) {

            throw new FileUploadException("A file of that name already exists.");
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String generateNewFileName(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null){
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String randomId = UUID.randomUUID().toString();
            return randomId + fileExtension;
        } else {
            throw new IllegalArgumentException("Le nom de fichier original est null !");
        }

    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    @Override
    public void deleteByName(String name){
        FileSystemUtils.deleteRecursively(this.root.resolve(name).toFile());
    }
}
