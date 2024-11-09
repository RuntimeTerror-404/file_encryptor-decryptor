# File Encryption and Compression/Decompression System

## Overview

This project provides a **File Encryption and Compression/Decompression System** built with **Java Spring Boot**. The application allows users to securely encrypt and decrypt text, audio, and image files. Additionally, it offers **file compression** and **decompression** features, enabling users to work with compressed and encrypted file formats seamlessly. The system ensures that sensitive data can be securely stored or transferred, and provides the ability to manage file metadata.

### Key Features:
- **Encrypt Files**: Securely encrypt text, image, and audio files using AES encryption.
- **Decrypt Files**: Decrypt files back to their original format.
- **Compression & Decompression**: Compress files before encryption, and decompress files after decryption.
- **File Metadata**: Tracks important file metadata (e.g., name, size, encryption status) and stores it for easy access.
- **No Database Dependency**: All file metadata is stored locally on the file system.

## Tech Stack:
- **Backend**: Java, Spring Boot
- **Encryption**: AES (Advanced Encryption Standard)
- **Compression**: Zip Compression (using `java.util.zip` package)
- **File Storage**: Local file system (no database used)
- **File Handling**: MultipartFile handling for uploading files via HTTP requests

## Problem Solved:
This application provides an end-to-end solution for securely handling files:
- **File Encryption/Decryption** ensures sensitive data remains protected.
- **Compression** allows users to save storage space before encryption and decompress the files after decryption.
- **Metadata management** enables easy tracking of encrypted files, with details such as file name, size, and encryption status.

## Getting Started

### Prerequisites:
- **JDK 17 or above**: Ensure you have Java Development Kit (JDK) installed.
- **Maven**: Used for building the project.
- **IDE**: Any Java IDE (IntelliJ IDEA, Eclipse, etc.).

### Setup Instructions:
1. **Clone the Repository:**
   ```bash
   git clone https://github.com/yourusername/file-encryptor-decryptor.git
   cd file-encryptor-decryptor
