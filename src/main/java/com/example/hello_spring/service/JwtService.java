package com.example.hello_spring.service;

import com.example.hello_spring.service.db.entity.UserEntity;
import com.example.hello_spring.service.db.repo.UserRepository;
import com.example.hello_spring.model.request.LoginRequest;
import com.example.hello_spring.model.response.LoginResponse;
import com.example.hello_spring.utility.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JwtService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtility jwtUtility;
    @Autowired
    private AuthenticationManager authenticationManager;


    public LoginResponse createJwtToken(LoginRequest loginRequest) throws Exception {
        LoginResponse loginResponse = new LoginResponse();
        String userName = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        authenticate(userName, password);
        final UserDetails userDetails = loadUserByUsername(userName);
        Map<String, String> tokens = this.jwtUtility.generateToken(userDetails);
        loginResponse.setAccessToken(tokens.get("access_token"));
        loginResponse.setRefreshToken(tokens.get("refresh_token"));
        return loginResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found!");
        }
        return user;

    }

    private void authenticate(String userName, String password) throws Exception {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        } catch (BadCredentialsException e) {
            throw new Exception("Bad Credentials");
        }

    }
}
