package com.example.hello_spring.service;

import com.example.hello_spring.model.request.UserRequest;

import com.example.hello_spring.model.response.UserResponse;

import com.example.hello_spring.service.db.entity.UserEntity;
import com.example.hello_spring.service.db.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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

    @Override
    public UserResponse userById(long id) {
        UserEntity firstById = userRepository.findFirstById(id);
        if (firstById == null) {
            logger.error("user id not found", (Object) null);
            return null;
        } else {
            UserResponse response = new UserResponse();
            response.setUsername(firstById.getUsername());
            response.setId(firstById.getId());

            return response;
        }
    }

    private void buildUserEntity(UserEntity userEntity, UserRequest userRequest) {
        userEntity.setUsername(userRequest.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
    }


}