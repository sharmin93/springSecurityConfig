package com.example.hello_spring.service;

import com.example.hello_spring.db.entity.UserEntity;
import com.example.hello_spring.db.repo.UserRepository;

import com.example.hello_spring.model.request.UserRequest;

import com.example.hello_spring.model.response.UserResponse;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;


    }

    @Override
    public UserResponse saveName(UserRequest userRequest) {
        UserResponse userResponse = new UserResponse();
        UserEntity userEntity = new UserEntity();
        buildUserEntity(userEntity, userRequest);
        userRepository.save(userEntity);
        userResponse.setId(userEntity.getId());
        return userResponse;
    }

    private void buildUserEntity(UserEntity userEntity, UserRequest userRequest) {

        userEntity.setUsername(userRequest.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
    }


}
