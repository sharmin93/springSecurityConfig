package com.example.hello_spring.service;
import com.example.hello_spring.model.response.UserResponse;
import com.example.hello_spring.service.db.entity.UserEntity;
import com.example.hello_spring.service.db.repo.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


import static org.mockito.Mockito.verify;


@SpringBootTest
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userServiceTest;

    @Mock
    private UserRepository userRepositoryTest;


    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        UserEntity user =new UserEntity(1L, "user", "pass");
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