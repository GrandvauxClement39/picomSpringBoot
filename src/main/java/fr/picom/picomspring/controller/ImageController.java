package fr.picom.picomspring.controller;

import fr.picom.picomspring.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    @Autowired
    private FilesStorageService filesStorageService;

    @GetMapping("/assets/{nomImage:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String nomImage) {

        Resource image = filesStorageService.load(nomImage);
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + image.getFilename() + "\""
                )
                .body(image);
    }
}
