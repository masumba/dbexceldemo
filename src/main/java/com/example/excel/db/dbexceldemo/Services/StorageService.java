package com.example.excel.db.dbexceldemo.Services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final Path rootLocation = Paths.get("upload-dir");

    public void store(MultipartFile file){
        try{
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (Exception e){
            throw new RuntimeException("Fail! Service Error: "+e);
        }
    }

    public Resource loadFile(String filename){
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (((UrlResource) resource).exists() || ((UrlResource) resource).isReadable()){
                return resource;
            } else {
                throw new RuntimeException("Failed to load file!");
            }
        } catch (MalformedURLException e){
            throw new RuntimeException("Fail Load Error: "+e);
        }
    }

    public void deleteAll(){
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void init(){
        try{
            Files.createDirectory(rootLocation);
        } catch (IOException e){
            throw new RuntimeException("Could Not Create Storage: "+e);
        }
    }

}