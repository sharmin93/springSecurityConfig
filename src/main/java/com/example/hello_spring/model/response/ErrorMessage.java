package com.example.hello_spring.model.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorMessage {
    private String errorMessage;
    private HttpStatus statusCode;
}
