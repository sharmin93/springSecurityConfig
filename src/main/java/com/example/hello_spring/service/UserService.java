package com.example.hello_spring.service;

import com.example.hello_spring.model.request.UserRequest;

import com.example.hello_spring.model.response.UserResponse;

public interface UserService {

    UserResponse saveName(UserRequest userRequest);

    UserResponse userById(long id) ;


}


