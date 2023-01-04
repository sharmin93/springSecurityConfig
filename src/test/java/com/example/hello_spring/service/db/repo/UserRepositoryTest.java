//package com.example.hello_spring.service.db.repo;
//
//import com.example.hello_spring.service.UserService;
//import com.example.hello_spring.service.UserServiceImpl;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//
//import static org.mockito.Mockito.verify;
//
//
//class UserRepositoryTest {
//    @Mock
//    private  UserRepository userRepository;
//    private AutoCloseable autoCloseable;
//    private UserService userService;
//
//
//    @BeforeEach
//    void setUp() {
//        userService = new UserServiceImpl(userRepository);
//        autoCloseable = MockitoAnnotations.openMocks(this);
//    }
//
//    @AfterEach
//    void tearDown() throws Exception {
//        autoCloseable.close();
//    }
//
//    @Test
//    void userById() {
//        userService.userById(902);
//        verify(userRepository).findFirstById(902);
//    }
//}