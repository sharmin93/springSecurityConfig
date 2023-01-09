package com.example.hello_spring.controller;

import com.example.hello_spring.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(value = "api/v1")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        HashMap<String, Object> res = new HashMap<>();
        res = tokenService.getTokenByLastOneHour();
        return ResponseEntity.ok().body(res);
    }

}
