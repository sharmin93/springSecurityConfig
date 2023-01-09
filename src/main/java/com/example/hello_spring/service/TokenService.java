package com.example.hello_spring.service;

import com.example.hello_spring.service.db.entity.TokenManager;
import com.example.hello_spring.service.db.repo.TokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class TokenService {

    private final TokenRepository tokenManagerRepository;

    public TokenService(TokenRepository tokenManagerRepository) {
        this.tokenManagerRepository = tokenManagerRepository;
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
}
