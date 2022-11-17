package com.petsAdoption.fileStroage;

import com.petsAdoption.fileStroage.utils.FileNameCreator;
import jdk.nashorn.internal.ir.ReturnNode;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class FileStorageApp {
    public static void main(String[] args) {
        SpringApplication.run(FileStorageApp.class, args);
    }

    @Bean
    public FileNameCreator fileNameCreator(){
        return new FileNameCreator();
    }
}
