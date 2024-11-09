package com.example.encryptor_decryptor.controller;


import com.example.encryptor_decryptor.FileMetadata;
import com.example.encryptor_decryptor.MetadataManager;
import com.example.encryptor_decryptor.utility.FileHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private MetadataManager metadataManager = new MetadataManager();

    private final FileHandler fileHandler;
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    public FileController(FileHandler fileHandler, MetadataManager metadataManager) {
        this.fileHandler = fileHandler;
        this.metadataManager = metadataManager;
    }

    // Endpoint for encryption (includes compression before encryption)
    @PostMapping("/encrypt")
    public ResponseEntity<String> encryptFile(@RequestParam("file") MultipartFile file) {
        try {
            logger.info("Received request to encrypt file: {}", file.getOriginalFilename());
            // Calls the encryptFile method in FileHandler, which handles compression before encryption
            String encryptedFilePath = fileHandler.encryptFile(file);
            return ResponseEntity.ok("File encrypted successfully! Saved at: " + encryptedFilePath);
        } catch (Exception e) {
            logger.error("File encryption failed", e);
            return ResponseEntity.status(500).body("File encryption failed: " + e.getMessage());
        }
    }

    // Endpoint for decryption (includes decompression after decryption)
    @PostMapping("/decrypt")
    public ResponseEntity<String> decryptFile(@RequestParam("file") MultipartFile file) {
        try {
            logger.info("Received request to decrypt file: {}", file.getOriginalFilename());
            // Calls the decryptFile method in FileHandler, which handles decompression after decryption
            String decryptedFilePath = fileHandler.decryptFile(file);
            return ResponseEntity.ok("File decrypted successfully! Saved at: " + decryptedFilePath);
        } catch (Exception e) {
            logger.error("File decryption failed", e);
            return ResponseEntity.status(500).body("File decryption failed: " + e.getMessage());
        }
    }

    // New Endpoint for Fetching File Metadata
    @GetMapping("/metadata")
    public ResponseEntity<List<FileMetadata>> getAllFileMetadata() {
        try {
            List<FileMetadata> metadataList = metadataManager.getAllMetadata();
            return ResponseEntity.ok(metadataList);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}








