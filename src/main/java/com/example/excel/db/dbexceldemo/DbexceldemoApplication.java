package com.example.excel.db.dbexceldemo;



import com.example.excel.db.dbexceldemo.Services.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

import javax.annotation.Resource;

@SpringBootApplication
public class DbexceldemoApplication implements CommandLineRunner {


    @Resource
    StorageService storageService;

    public static void main(String[] args){
        SpringApplication.run(DbexceldemoApplication.class, args);
    }

    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }

    @Override
    public void run(String... args) throws Exception{
        storageService.deleteAll();
        storageService.init();
    }
}
