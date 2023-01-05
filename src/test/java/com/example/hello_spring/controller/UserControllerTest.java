package com.example.hello_spring.controller;

import com.example.hello_spring.model.request.UserRequest;
import com.example.hello_spring.model.response.UserResponse;
import com.example.hello_spring.service.db.entity.UserEntity;
import com.example.hello_spring.service.db.repo.TestH2Repository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class UserControllerTest {

    private String baseUrl;
    private static RestTemplate restTemplate;
//    @LocalServerPort
//    private  int port;
    @Autowired
    private TestH2Repository testH2Repository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    void setUp() {
        baseUrl ="http://localhost:8003/api/v1/user";
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testUserInfoAdd() {
        UserEntity userEntity = new UserEntity("auth08", "1234");
        UserEntity userEntityResponse = restTemplate.postForObject(baseUrl, userEntity, UserEntity.class);
        assert userEntityResponse != null;
        assertEquals("auth08", userEntityResponse.getUsername());


    }

    @Test
    @Disabled
    void userById() {
    }
}