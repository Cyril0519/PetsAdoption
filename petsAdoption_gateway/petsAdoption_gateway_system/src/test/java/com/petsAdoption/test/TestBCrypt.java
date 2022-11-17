package com.petsAdoption.test;


import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

@SpringBootTest
public class TestBCrypt {


    @Test
    public void test(){
        // 获取盐
        String salt = BCrypt.gensalt();
        System.out.println(salt);
        // 基于当前的盐对密码加密
        String hashpw = BCrypt.hashpw("123456", salt);
        System.out.println(hashpw);

        // 解密
        boolean checkpw = BCrypt.checkpw("123456", hashpw);
        System.out.println(checkpw);
    }
}
