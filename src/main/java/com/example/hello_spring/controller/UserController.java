package com.example.hello_spring.controller;

import com.example.hello_spring.model.request.UserRequest;
import com.example.hello_spring.model.response.UserResponse;
import com.example.hello_spring.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/hello")
    String helloMessage() {
        String message = "hello to this page.";
        return message;
    }

    @GetMapping(value = "/welcome")
    String welcomeMessage() {
        String message = "welcome to this page.";

        return message;
    }

    @PostMapping(value = "/user")
    public ResponseEntity<UserResponse> userInfo(@RequestBody UserRequest userRequest) {
        final Logger logger= LoggerFactory.getLogger(AuthController.class);
        UserResponse userResponse = userService.saveName(userRequest);
        logger.info("create user");
        return new ResponseEntity<>(userResponse, HttpStatus.OK);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponse> userById(@PathVariable("id") Long id){
        UserResponse userResponse = userService.userById(id);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

}
