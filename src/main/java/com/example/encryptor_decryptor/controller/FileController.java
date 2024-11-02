package com.example.encryptor_decryptor.controller;


import com.example.encryptor_decryptor.utility.FileHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileHandler fileHandler;

    public FileController(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @PostMapping("/encrypt")
    public ResponseEntity<String> encryptFile(@RequestParam("file") MultipartFile file) {
        try {
            String encryptedFilePath = fileHandler.encryptFile(file);
            return ResponseEntity.ok("File encrypted successfully! Saved at: " + encryptedFilePath);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("File encryption failed: " + e.getMessage());
        }
    }

    @PostMapping("/decrypt")
    public ResponseEntity<String> decryptFile(@RequestParam("file") MultipartFile file) {
        try {
            String decryptedFilePath = fileHandler.decryptFile(file);
            return ResponseEntity.ok("File decrypted successfully! Saved at: " + decryptedFilePath);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("File decryption failed: " + e.getMessage());
        }
    }
}
