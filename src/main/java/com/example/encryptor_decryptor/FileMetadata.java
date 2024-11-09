package com.example.encryptor_decryptor;

public class FileMetadata {

    private String fileName;
    private long fileSize;
    private String fileType;
    private String encryptionStatus; // "Encrypted" or "Decrypted"
    private String path; // Location of the file (local path)

    // Getters and Setters
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getEncryptionStatus() {
        return encryptionStatus;
    }

    public void setEncryptionStatus(String encryptionStatus) {
        this.encryptionStatus = encryptionStatus;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    // Optional: Override toString() method for better display of metadata
    @Override
    public String toString() {
        return "FileMetadata{" +
                "fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                ", fileType='" + fileType + '\'' +
                ", encryptionStatus='" + encryptionStatus + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
