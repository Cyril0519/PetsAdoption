package com.petsAdoption.oauth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Base64Utils;

@SpringBootTest
public class Base64Test {
    @Test
    public void test1() {
        byte[] bytes = Base64Utils.decodeFromString("MTIzMTIz");
        System.out.println(new String(bytes));
    }
}
