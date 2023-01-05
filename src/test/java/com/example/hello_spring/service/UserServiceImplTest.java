package com.example.hello_spring.service;
import com.example.hello_spring.service.db.repo.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import static org.mockito.Mockito.verify;


@SpringBootTest
class UserServiceImplTest {
    @MockBean
    private UserRepository userRepositoryTest;

    @InjectMocks
    private UserServiceImpl userServiceTest;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        userServiceTest = new UserServiceImpl(userRepositoryTest, passwordEncoder);
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testUserById() {
        long id = 1;
        userServiceTest.userById(id);
        verify(userRepositoryTest).findFirstById(id);
    }

}