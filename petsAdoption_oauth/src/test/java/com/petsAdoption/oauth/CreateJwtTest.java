package com.petsAdoption.oauth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class CreateJwtTest {

    @Test
    public void createJWT() {
        // 基于私钥生成jwt
        // 1.创建秘钥工厂
        // 1： 指定私钥位置
        ClassPathResource classPathResource = new ClassPathResource("petsAdoption.jks");
        // 2：秘钥库密码
        String keyPass = "petsAdoption";
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, keyPass.toCharArray());

        // 2.基于工厂获取私钥
        String alias = "petsAdoption";
        String password = "petsAdoption";
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias, password.toCharArray());
        // 将私钥转为rsa私钥
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        // 3.生成jwt
        Map<String, String> map = new HashMap<>();
        map.put("company", "wxy");
        map.put("address", "gz");
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(map), new RsaSigner(rsaPrivateKey));
        String jwtEncoded = jwt.getEncoded();
        System.out.println(jwtEncoded);
    }
   
}
