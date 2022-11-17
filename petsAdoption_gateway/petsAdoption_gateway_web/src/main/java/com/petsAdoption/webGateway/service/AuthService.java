package com.petsAdoption.webGateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String getJtiFromCookie(ServerHttpRequest request) {
        HttpCookie uid = request.getCookies().getFirst("uid");
        if (uid!=null) {
            String jti = uid.getValue();
            return jti;
        }
        return null;
    }

    public String getJwtFromRedis(String jti) {
        String jwt = stringRedisTemplate.boundValueOps(jti).get();
        return jwt;
    }
}
