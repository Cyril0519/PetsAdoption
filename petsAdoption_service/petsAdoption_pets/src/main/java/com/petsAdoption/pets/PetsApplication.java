package com.petsAdoption.pets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.petsAdoption.user.feign"})
public class PetsApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetsApplication.class, args);
    }
}
