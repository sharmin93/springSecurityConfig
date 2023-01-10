package com.example.hello_spring.service;

import com.example.hello_spring.service.db.entity.TokenManager;
import com.example.hello_spring.service.db.repo.TokenRepository;
import com.example.hello_spring.utility.JwtUtility;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest {

    @InjectMocks
    private TokenService tokenManagerServiceTest;
    @Mock
    private TokenRepository tokenManagerRepositoryTest;


    private String token;
    private String token2;
    private String token3;
    private AutoCloseable autoCloseable;
    private TokenManager tokenManager;
    private TokenManager tokenManager2;
    @Mock
    private JwtUtility jwtUtil;

    @BeforeEach
    void setUp() {
        token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNjczMjU0NDg2LCJleHAiOjE2NzMyNTQ2MDZ9.C-zP2SKtS3z51ARgPQF6Sdj2nybn-tfGIXFywowo21I";
        token2 = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMiIsImlhdCI6MTY3MzI0Njk0OCwiZXhwIjoxNjczMjQ3ODQ4fQ.ZGSXuzj4J1x1s2e7bzmFGokgdNaY46uMVxmZ7ZLM9b4";
        tokenManager = new TokenManager(1L, "user", token, true, LocalDateTime.now());

        System.out.println("token" + token);
        tokenManager = new TokenManager(1L, "user", token, true, LocalDateTime.now());
        autoCloseable = MockitoAnnotations.openMocks(this);

    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getTokenByLastOneHour() {
        List<TokenManager> tokenManagerList = new ArrayList<>();
        token2 = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMiIsImlhdCI6MTY3MzI1NDQ4NiwiZXhwIjoxNjczMjU0NjA2fQ.8jW4hYl3hg4sciTS3Tpe-VMhRRZMGUfow0p6q0WvF_I";
        token3 = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMiIsImlhdCI6MTY3MzI1NDQ4NiwiZXhwIjoxNjczMjcyNDg2fQ.KA0Mwv5717-YQ0kG4UKYvRDpJPbx_xeGP-JxJAUQ0Ok";
        tokenManager2 = new TokenManager(2L, "user2", token2, true, LocalDateTime.now());
        tokenManager2 = new TokenManager(3L, "user3", token3, true, LocalDateTime.now());
        tokenManagerList.add(tokenManager);
        tokenManagerList.add(tokenManager2);
        LocalDateTime currentTime = LocalDateTime.now();

        Mockito.when(tokenManagerRepositoryTest.getTokenManagerByLastOneHour(Mockito.any(LocalDateTime.class))).thenReturn(tokenManagerList);
        HashMap<String, Object> tokenByLastOneHour = tokenManagerServiceTest.getTokenByLastOneHour();
        Assertions.assertEquals(true, tokenByLastOneHour.get("status"));
        List<TokenManager> dataList = (List<TokenManager>) tokenByLastOneHour.get("data");
        Assertions.assertEquals(true, tokenByLastOneHour.get("status"));
        Assertions.assertEquals(2, dataList.size());
    }

    @Test
    void testKillSession() {
        String token = "token";
        String currentUser = "admin";

        String authHeader = "Bearer" + " " + token;
        String userName = "user";

        Mockito.when(tokenManagerRepositoryTest.killSession(userName)).thenReturn(2);
        Mockito.when(jwtUtil.getUserNameFromToken(token)).thenReturn(currentUser);

        HashMap<String, Object> sessionData = tokenManagerServiceTest.killSession(authHeader, userName);
        Assertions.assertEquals(true, sessionData.get("status"));
        Assertions.assertEquals(2, sessionData.get("data"));


    }

    @Test
    void canNotKillSelfSession() {
        String token = "token";
        String currentUser = "admin";
        System.out.println("currentUser" + " " + currentUser);
        String authHeader = "Bearer" + " " + token;
        String userName = "admin";


        Mockito.when(jwtUtil.getUserNameFromToken(token)).thenReturn(currentUser);

        HashMap<String, Object> sessionData = tokenManagerServiceTest.killSession(authHeader, userName);
        Assertions.assertEquals(true, sessionData.get("status"));
        Assertions.assertEquals("You are trying to kill your session", sessionData.get("message"));
        Assertions.assertNull(sessionData.get("data"));


    }
}