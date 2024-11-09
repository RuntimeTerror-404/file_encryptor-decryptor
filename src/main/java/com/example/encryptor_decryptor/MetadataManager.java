package com.example.encryptor_decryptor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MetadataManager {

    private final String metadataFilePath = "file_metadata.json"; // Location to store metadata file

    public void addMetadata(FileMetadata metadata) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        // Check if the metadata file already exists
        File metadataFile = new File(metadataFilePath);
        List<FileMetadata> metadataList;

        if (metadataFile.exists()) {
            metadataList = objectMapper.readValue(metadataFile, new TypeReference<List<FileMetadata>>() {});
        } else {
            metadataList = new ArrayList<>();
        }

        // Add new metadata and save to the file
        metadataList.add(metadata);
        objectMapper.writeValue(metadataFile, metadataList);
    }

    public List<FileMetadata> getAllMetadata() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File metadataFile = new File(metadataFilePath);

        if (metadataFile.exists()) {
            return objectMapper.readValue(metadataFile, new TypeReference<List<FileMetadata>>() {});
        } else {
            return new ArrayList<>();
        }
    }
}

