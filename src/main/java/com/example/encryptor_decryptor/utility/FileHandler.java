package com.example.encryptor_decryptor.utility;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileHandler {

    private final String uploadDir = "C:/Personal/Tools/Java_Projects/encryptor_decryptor/files"; // Update this to your actual upload path

    public FileHandler() {
        createDirectoryIfNotExists(uploadDir);
    }

    public String encryptFile(MultipartFile file) throws Exception {
        File convertedFile = convertMultipartFileToFile(file);

        // Perform encryption logic here
        String encryptedFilePath = uploadDir + "/encrypted_" + file.getOriginalFilename();
        Files.write(Paths.get(encryptedFilePath), /* encrypted data */ new byte[0]); // Replace with actual encrypted data

        return encryptedFilePath;
    }

    public String decryptFile(MultipartFile file) throws Exception {
        File convertedFile = convertMultipartFileToFile(file);

        // Perform decryption logic here
        String decryptedFilePath = uploadDir + "/decrypted_" + file.getOriginalFilename();
        Files.write(Paths.get(decryptedFilePath), /* decrypted data */ new byte[0]); // Replace with actual decrypted data

        return decryptedFilePath;
    }

    private File convertMultipartFileToFile(MultipartFile file) throws Exception {
        File convFile = new File(uploadDir + "/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }

    private void createDirectoryIfNotExists(String path) {
        Path directory = Paths.get(path);
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create directory: " + path, e);
            }
        }
    }
}
