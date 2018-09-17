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

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private final Path uploadsRootLocation = Paths.get("upload-dir");

    public void store(MultipartFile file){
        try{
            Files.copy(file.getInputStream(), this.uploadsRootLocation.resolve(file.getOriginalFilename()));
        } catch (Exception e){
            throw new RuntimeException("Fail! Service Error: "+e);
        }
    }

    public Resource loadFile(String filename){
        try {
            Path file = uploadsRootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (((UrlResource) resource).exists() || ((UrlResource)resource).isReadable()){
                return resource;
            } else {
                throw new RuntimeException("Failed to load file!");
            }
        } catch (MalformedURLException e){
            throw new RuntimeException("Failed to Load FILE Error: "+e);
        }
    }

    public void deleteAll(){
        FileSystemUtils.deleteRecursively(uploadsRootLocation.toFile());
    }

    public void init(){
        try {
            Files.createDirectory(uploadsRootLocation);
        } catch (IOException e){
            throw new RuntimeException("Could Not Create Storage: "+e);
        }
    }

}
