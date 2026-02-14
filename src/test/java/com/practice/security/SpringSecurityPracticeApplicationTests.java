package com.practice.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:postgresql://localhost:5432/security_practice_test",
    "spring.data.redis.host=localhost",
    "spring.data.redis.port=6379"
})
class SpringSecurityPracticeApplicationTests {

    @Test
    void contextLoads() {
    }
}
