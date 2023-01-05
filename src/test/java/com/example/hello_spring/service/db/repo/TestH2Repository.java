package com.example.hello_spring.service.db.repo;

import com.example.hello_spring.model.request.UserRequest;

import com.example.hello_spring.service.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2Repository extends JpaRepository<UserEntity,Long> {

}
