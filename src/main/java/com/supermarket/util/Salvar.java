package com.supermarket.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Salvar {

    private static String pathImage = "C:\\Users\\Caioa\\OneDrive\\Imagens\\Saved Pictures\\";

    public static String saveImage(MultipartFile file) {
        try {
            if(!file.isEmpty()) {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(pathImage+String.valueOf(file.getOriginalFilename()));
                Files.write(path, bytes);
                return (String.valueOf(file.getOriginalFilename()));
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }
}
