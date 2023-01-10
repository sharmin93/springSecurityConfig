package com.example.hello_spring.service;

import com.example.hello_spring.service.db.entity.TokenManager;
import com.example.hello_spring.service.db.repo.TokenRepository;
import com.example.hello_spring.utility.JwtUtility;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class TokenService {

    private final TokenRepository tokenManagerRepository;
    private final JwtUtility jwtUtil;

    public TokenService(TokenRepository tokenManagerRepository, JwtUtility jwtUtil) {
        this.tokenManagerRepository = tokenManagerRepository;
        this.jwtUtil = jwtUtil;
    }

    public HashMap<String, Object> getTokenByLastOneHour() {
        HashMap<String, Object> res = new HashMap<>();
        LocalDateTime currentTime = LocalDateTime.now();
        try {
            List<TokenManager> tokenManagerList = tokenManagerRepository.getTokenManagerByLastOneHour(currentTime.minusHours(1));
            res.put("status", true);
            res.put("data", tokenManagerList);
        } catch (Exception ex) {
            res.put("status", false);
            res.put("data", null);

        }
        return res;
    }

    @Transactional
    public HashMap<String, Object> killSession(String authHeader, String userName) {
        HashMap<String, Object> res = new HashMap<>();
        String token = null;
        String currentUser = null;
        final String header = authHeader;
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            try {
                currentUser = jwtUtil.getUserNameFromToken(token);
            } catch (IllegalArgumentException e) {

                e.printStackTrace();
            } catch (ExpiredJwtException e) {

                e.printStackTrace();
            }
        } else {

        }
        if (userName == currentUser || currentUser.equals(userName)) {
            res.put("status", true);
            res.put("data", null);
            res.put("message", "You are trying to kill your session");

            return res;
        }
        try {
            int numberOfSession = tokenManagerRepository.killSession(userName);
            res.put("status", true);
            res.put("data", numberOfSession);

        } catch (Exception ex) {
            res.put("status", false);
            res.put("data", null);

        }
        return res;
    }
}
