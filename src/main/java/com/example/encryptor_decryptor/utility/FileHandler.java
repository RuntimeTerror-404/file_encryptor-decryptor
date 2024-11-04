package com.example.encryptor_decryptor.utility;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@Service
public class FileHandler {

    private final String uploadDir = "C:/Personal/Tools/Java_Projects/encryptor_decryptor/files";
    private final Key secretKey;

    public FileHandler() throws Exception {
        createDirectoryIfNotExists(uploadDir);
        this.secretKey = generateSecretKey();
    }

    public String encryptFile(MultipartFile file) throws Exception {
        Path encryptedFilePath = Paths.get(uploadDir, file.getOriginalFilename() + ".enc");
        encryptFile(file, encryptedFilePath, secretKey);
        return encryptedFilePath.toString();
    }

    public String decryptFile(MultipartFile file) throws Exception {
        Path decryptedFilePath = Paths.get(uploadDir, file.getOriginalFilename().replace(".enc", ""));
        decryptFile(file, decryptedFilePath, secretKey);
        return decryptedFilePath.toString();
    }

    private void encryptFile(MultipartFile file, Path encryptedFilePath, Key key) throws Exception {
        try (InputStream inputStream = file.getInputStream();
             OutputStream outputStream = Files.newOutputStream(encryptedFilePath)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] encryptedData = cipher.update(buffer, 0, bytesRead);
                outputStream.write(encryptedData);
            }
            outputStream.write(cipher.doFinal());
        }
    }

    private void decryptFile(MultipartFile file, Path decryptedFilePath, Key key) throws Exception {
        try (InputStream inputStream = file.getInputStream();
             OutputStream outputStream = Files.newOutputStream(decryptedFilePath)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] decryptedData = cipher.update(buffer, 0, bytesRead);
                outputStream.write(decryptedData);
            }
            outputStream.write(cipher.doFinal());
        }
    }

    private Key generateSecretKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // AES key size
        return keyGen.generateKey();
    }

    private void createDirectoryIfNotExists(String path) {
        Path directory = Paths.get(path);
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create directory: " + path, e);
            }
        }
    }
}

