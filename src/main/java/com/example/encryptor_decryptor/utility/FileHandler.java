package com.example.encryptor_decryptor.utility;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Base64;

@Service
public class FileHandler {

    private final String uploadDir = "C:/Personal/Tools/Java_Projects/encryptor_decryptor/files"; // Update to your path

    private final Key secretKey;

    public FileHandler() throws Exception {
        createDirectoryIfNotExists(uploadDir);
        this.secretKey = generateSecretKey();
    }

    public String encryptFile(MultipartFile file) throws Exception {
        byte[] fileBytes = file.getBytes();

        // Encrypt the file bytes
        byte[] encryptedBytes = encrypt(fileBytes, secretKey);

        // Write encrypted data to the new file
        String encryptedFilePath = uploadDir + "/encrypted_" + file.getOriginalFilename();
        try (FileOutputStream fos = new FileOutputStream(encryptedFilePath)) {
            fos.write(encryptedBytes);
        }

        return encryptedFilePath;
    }

    public String decryptFile(MultipartFile file) throws Exception {
        byte[] encryptedBytes = file.getBytes();

        // Decrypt the file bytes
        byte[] decryptedBytes = decrypt(encryptedBytes, secretKey);

        // Write decrypted data to the new file
        String decryptedFilePath = uploadDir + "/decrypted_" + file.getOriginalFilename();
        try (FileOutputStream fos = new FileOutputStream(decryptedFilePath)) {
            fos.write(decryptedBytes);
        }

        return decryptedFilePath;
    }

    private byte[] encrypt(byte[] data, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    private byte[] decrypt(byte[] data, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    private Key generateSecretKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // AES key size (128, 192, or 256 bits)
        SecretKey secretKey = keyGen.generateKey();
        // Optionally, save this key securely for reuse
        return secretKey;
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
