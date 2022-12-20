package com.example.hello_spring.controller;


import com.example.hello_spring.config.JwtFilter;
import com.example.hello_spring.model.request.LoginRequest;
import com.example.hello_spring.model.response.LoginResponse;
import com.example.hello_spring.service.JwtService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "api/v1")
public class AuthController {
    @Autowired
    private JwtService jwtService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {

        final Logger logger= LoggerFactory.getLogger(AuthController.class);
        LoginResponse loginResponse = this.jwtService.createJwtToken(loginRequest);
        logger.info("successful login");
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }



}
