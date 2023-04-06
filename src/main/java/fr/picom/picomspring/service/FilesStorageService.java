package fr.picom.picomspring.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FilesStorageService {
    void init();

    void save(MultipartFile file, String newFileName);

    Resource load(String filename);

    void deleteAll();

    Stream<Path> loadAll();

    String generateNewFileName(MultipartFile file);
}
