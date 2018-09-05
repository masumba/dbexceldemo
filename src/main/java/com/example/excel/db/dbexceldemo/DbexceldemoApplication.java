package com.example.excel.db.dbexceldemo;


import com.example.excel.db.dbexceldemo.Service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class DbexceldemoApplication implements CommandLineRunner {


    @Resource
    StorageService storageService;

    public static void main(String[] args){
        SpringApplication.run(DbexceldemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception{
        storageService.deleteAll();
        storageService.init();
    }
}
