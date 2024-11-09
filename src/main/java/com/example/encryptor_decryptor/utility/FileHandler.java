package com.example.encryptor_decryptor.utility;

import com.example.encryptor_decryptor.FileMetadata;
import com.example.encryptor_decryptor.MetadataManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.io.*;
import java.nio.file.*;
import java.security.Key;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FileHandler {

    private static final Logger logger = LoggerFactory.getLogger(FileHandler.class);
    private MetadataManager metadataManager = new MetadataManager();

    private final String uploadDir = "C:/Personal/Tools/Java_Projects/encryptor_decryptor/files";
    private final Key secretKey;

    public FileHandler() throws Exception {
        createDirectoryIfNotExists(uploadDir);
        this.secretKey = generateSecretKey();
        logger.info("FileHandler initialized. Upload directory: {}", uploadDir);
    }

    public String encryptFile(MultipartFile file) throws Exception {
        // Compress the file first
        Path compressedFilePath = compressFile(file);
        Path encryptedFilePath = Paths.get(uploadDir, compressedFilePath.getFileName() + ".enc");

        // Store metadata
        FileMetadata metadata = new FileMetadata();
        metadata.setFileName(file.getOriginalFilename());
        metadata.setFileSize(file.getSize());
        metadata.setFileType(file.getContentType());
        metadata.setEncryptionStatus("Encrypted");
        metadata.setPath(encryptedFilePath.toString());

        metadataManager.addMetadata(metadata);

        logger.info("Encrypting file: {}", compressedFilePath.getFileName());

        // Encrypt the compressed file
        encryptFile(Files.newInputStream(compressedFilePath), encryptedFilePath, secretKey);

        logger.info("File encrypted successfully: {}", encryptedFilePath);

        Files.deleteIfExists(compressedFilePath); // Clean up compressed file
        return encryptedFilePath.toString();
    }

    public String decryptFile(MultipartFile file) throws Exception {
        Path decryptedFilePath = Paths.get(uploadDir, file.getOriginalFilename().replace(".enc", ".zip"));
        logger.info("Decrypting file: {}", file.getOriginalFilename());

        // Store metadata
        FileMetadata metadata = new FileMetadata();
        metadata.setFileName(file.getOriginalFilename());
        metadata.setFileSize(file.getSize());
        metadata.setFileType(file.getContentType());
        metadata.setEncryptionStatus("Decrypted");
        metadata.setPath(decryptedFilePath.toString());

        metadataManager.addMetadata(metadata);

        // Decrypt the encrypted file
        decryptFile(file.getInputStream(), decryptedFilePath, secretKey);

        logger.info("File decrypted successfully: {}", decryptedFilePath);

        // Decompress the decrypted file
        Path decompressedFilePath = decompressFile(decryptedFilePath);

        Files.deleteIfExists(decryptedFilePath); // Clean up decrypted zip file
        return decompressedFilePath.toString();
    }

    private void encryptFile(InputStream inputStream, Path encryptedFilePath, Key key) throws Exception {
        try (OutputStream outputStream = Files.newOutputStream(encryptedFilePath)) {
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

    private void decryptFile(InputStream inputStream, Path decryptedFilePath, Key key) throws Exception {
        try (OutputStream outputStream = Files.newOutputStream(decryptedFilePath)) {
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

    private Path compressFile(MultipartFile file) throws IOException {
        Path compressedFilePath = Paths.get(uploadDir, file.getOriginalFilename() + ".zip");
        try (InputStream inputStream = file.getInputStream();
             ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(compressedFilePath))) {

            ZipEntry zipEntry = new ZipEntry(file.getOriginalFilename());
            zipOutputStream.putNextEntry(zipEntry);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                zipOutputStream.write(buffer, 0, bytesRead);
            }
            zipOutputStream.closeEntry();
        }
        return compressedFilePath;
    }

    private Path decompressFile(Path compressedFilePath) throws IOException {
        Path decompressedFilePath = Paths.get(uploadDir, compressedFilePath.getFileName().toString().replace(".zip", ""));

        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(compressedFilePath))) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            if (zipEntry != null) {
                try (OutputStream outputStream = Files.newOutputStream(decompressedFilePath)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = zipInputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
                zipInputStream.closeEntry();
            }
        }
        return decompressedFilePath;
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
