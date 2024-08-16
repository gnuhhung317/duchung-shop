package net.duchung.shop_app.component;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilTest {
    @Autowired
    private JwtUtil jwtUtil ;
    @Test
    public void testExtract(){
        String token ="eyJhbGciOiJIUzUxMiJ9.eyJwaG9uZU51bWJlciI6IjEyMzQ1Njc4OTAiLCJzdWIiOiIxMjM0NTY3ODkwIiwiZXhwIjoxNzIzMDE0OTI5fQ.d7XbbN6GCaQ5NqN-ndBnE7nC65AQqxjHZ9CmD_dOmHAZkgLLO5s5MXTvJkYYPnFl3uIq_8ETlxBxpWoGY9Kj4Q";
        String phoneNumber= jwtUtil.extractPhoneNumber(token);
        System.out.println(phoneNumber);
    }
}