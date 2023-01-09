package com.example.hello_spring.service.db.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@Table()
public class TokenManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String token;

    private Boolean isActive;

    private LocalDateTime createdTime;


    public TokenManager() {

    }
}
