package com.example.hello_spring.model.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
