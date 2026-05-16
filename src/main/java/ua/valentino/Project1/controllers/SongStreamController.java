package ua.valentino.Project1.controllers;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
@Controller
@RequestMapping("/songs")
public class SongStreamController {
    @Value("${upload.dir}")
    private String uploadDir;

    @GetMapping("/play/{fileName}")
    public ResponseEntity<Resource> playSong(@PathVariable("fileName") String fileName) throws IOException {
        Path path = Paths.get(uploadDir).resolve(fileName);
        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .contentType(MediaType.valueOf("audio/mpeg"))
                .body(resource);
    }
}
