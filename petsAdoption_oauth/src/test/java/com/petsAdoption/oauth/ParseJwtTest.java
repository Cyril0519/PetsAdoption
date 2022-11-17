package com.petsAdoption.oauth;

import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

public class ParseJwtTest {
    // eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZGRyZXNzIjoiZ3oiLCJjb21wYW55Ijoid3h5In0.av9Hgk0ah-mvcwm8lHBcQ9Am7EZDfxQXt8htzRfMosmdLoobIvOMIof3wsbKxfrnyWAFoJvgWdhe7vVjuElNCOrs6RxXvxjUmJ8VxQhiZ3LlO0W6qn1pGAAjNMiKuIOz-OS8jYwB9CyYnTlB1UlC4QE4zICTGhcq6ay4Rt3QOLybv9mS2SDTsWjJvqB6UipUt0qL-lgqXlS7JSCPzX9cOqnm9t12HkqSoExv6jixw0KdcLrB3R_aE1suC9pzt2OvSUnwAJiObvXOVj_Wa9RRmoZww7JwMnaCyAHG76UoY61TUfPUiPOIiDQFOZx8B8tpK-QeS63Iu5CkF6IjRmvO8Q
    @Test
    public void parseJWT() {
        // 基于公钥解析jwt
        // jwt
        String jwt = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZGRyZXNzIjoiZ3oiLCJjb21wYW55Ijoid3h5In0.UXGTQ5mVitJaQXRgf-TNZ37Qae9Y7Ypaq-09C3mO6rsc4PFERQBMKRaLNhbH7guIeeYy_P2rl7o4maMe2JcXPashk6EUHgWfuInI7sFIlvWoocXBAYP7S8SwAEV0Zy3z6ZQ9s759t8uyXsN1by0pTcRwTv8FntR8y8GlU0Pv7pVhEATuw7Js1fnbypFHsXer7CJJyCYNFsLIik189VjQVEwskEVpDB4_GwP9H9xjz_z0kE0-o21ajrAAbMwcUl_RPrkDI4ezo0PU0ewxfy58lYves4fRWIQIJk-TqRs3nexRL6tzoUJbYGIy3_TLQ1jxEQZvEltdOWI36Q_Xv45s_g";
        // 公钥
        String publicKey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsHzITIw0ChdvIzKpeqktgpX5CDuBvqrZ2sXjpVte1AXQTNpExGyWyyFouFtaChX9ZnrQ9UdPJvz8V8AN/4sry+Aw/9R0wON70MEIyFZHNoXQAFw+R3Ph9JmP6dbN5+Ih9InxvQ1b545nHQV5djz9Qoup3S9Yzxk04TWBdDp72i/XTM2pkLrq8Ykq48Vr3kirprqiHJVx9HQHGnTMKx2B45EKzuOJ12GlEDfBfUSvUaD7wbaiThsiB9VxbRYS+WcLBHChi2qQ1pEYWLP3zxtO3WJaSX2X7i81+BI08CopMpoChwGj6ZlyaX2q/q3PVXehTrEjKypKHyc/CNXSZhb5GwIDAQAB-----END PUBLIC KEY-----";
        Jwt token = JwtHelper.decodeAndVerify(jwt, new RsaVerifier(publicKey));
        String claims = token.getClaims();
        System.out.println(claims);

    }
}
