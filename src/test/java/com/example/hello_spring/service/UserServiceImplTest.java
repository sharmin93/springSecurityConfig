package com.example.hello_spring.service;

import com.example.hello_spring.model.response.UserResponse;
import com.example.hello_spring.service.db.entity.UserEntity;
import com.example.hello_spring.service.db.repo.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;







@SpringBootTest
class UserServiceImplTest {
    @Mock
    private UserRepository userRepositoryTest;

    @InjectMocks
    private UserServiceImpl userServiceTest;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        UserEntity user = new UserEntity(1L, "user", "pass");
        Mockito.when(userRepositoryTest.findFirstById(1L)).thenReturn(user);
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testUserById() {
        long id = 1;
        UserResponse userResponse = userServiceTest.userById(id);
        Assertions.assertEquals(userResponse.getId(), 1L);
        Assertions.assertEquals(userResponse.getUsername(), "user");
    }

}