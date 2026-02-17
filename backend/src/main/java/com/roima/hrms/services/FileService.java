package com.roima.hrms.services;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Service
public class FileService {

    public Object getFile(String fileType, String id) throws IOException {

        String BASE_PATH = "src/main/resources/";

        String fileDirectory = BASE_PATH + fileType;
;
        File file = new File(fileDirectory+"/"+id);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return resource;
    }

    public String fileUpload(MultipartFile file) throws IOException {
        File convertFile = new File("src/main/resources/" + file.getOriginalFilename());
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();
        return "File is upload successfully";
    }

    public void validateFile(MultipartFile file) {
        // null already check before calling this

        // checking size 20mb now
        int MAX_SIZE = 20 * 1024 * 1024;
        if (file.getSize() >= MAX_SIZE) {
            throw new IllegalArgumentException("File size must now Exceed 10mb");
        }

        // checking content type
        List<String> ALLOWED_TYPES = Arrays.asList("application/pdf", "image/jpeg", "image/png", "image/jpg");
        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException("File must be pdf or jpeg/jpg or png");
        }
    }

    //    public String store(MultipartFile file, String fileType, Long travelId, Long employeeId, Long index) throws IOException {
    public String store(MultipartFile file, String fileType) {
        String BASE_PATH = "src/main/resources/";

        // check here first
        // List<String> fileTypes = Arrays.asList("Expense","DocumentByHr");

        // getting type of file
        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

        // setting which folder to store this file
        String fileDirectory = BASE_PATH + fileType;
        // setting filename based on
        String fileName = UUID.randomUUID().toString() + fileExt;

        FileOutputStream fout = null;
        try {
            File convertFile = new File(fileDirectory + "/" + fileName);
            convertFile.createNewFile();
            fout = new FileOutputStream(convertFile);
            fout.write(file.getBytes());
            fout.close();
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file");
        }

        return fileName;
    }
}
