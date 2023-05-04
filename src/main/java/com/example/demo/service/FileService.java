package com.example.demo.service;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {

    @Value("${folder.upload}")
    private String folderUpload;

    public void transferToLocalFolder(MultipartFile file, String filename) throws IOException {
        Path path = Paths.get(folderUpload);
        Path destination = Paths.get(folderUpload + "\\" + filename);

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

    }

    public byte[] getImageByName(String imageName) throws FileNotFoundException {

        Path destination = Paths.get( folderUpload + "/" + imageName);

        try {
            return IOUtils.toByteArray((destination.toUri()));
        } catch (IOException e){
            throw new FileNotFoundException(e.getMessage());
        }
    }
}
