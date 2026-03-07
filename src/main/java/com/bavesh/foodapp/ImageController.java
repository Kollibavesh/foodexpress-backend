package com.bavesh.foodapp;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class ImageController {

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {

        File folder = new File(UPLOAD_DIR);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        File destination = new File(UPLOAD_DIR + fileName);

        file.transferTo(destination);

        return "http://localhost:8080/uploads/" + fileName;
    }

}