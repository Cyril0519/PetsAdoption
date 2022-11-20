package com.petsAdoption.wishList;

import com.petsAdoption.wishList.config.FeignInterceptor;
import com.petsAdoption.wishList.config.TokenDecode;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableFeignClients(basePackages = {"com.petsAdoption.user.feign","com.petsAdoption.pets.feign"})
@EnableDiscoveryClient
@EnableDubbo
public class WishListApplication {
    public static void main(String[] args) {
        SpringApplication.run(WishListApplication.class, args);
    }

    @Bean
    public TokenDecode tokenDecode() {
        return new TokenDecode();
    }

}
