package com.example.hello_spring.service.db.repo;


import com.example.hello_spring.service.db.entity.UserEntity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepositoryTest;

    UserEntity userEntity = new UserEntity();


    @Test
    void findByUsernameExists() {
        userEntity.setUsername("add");
        userEntity.setPassword("234");
        userRepositoryTest.save(userEntity);
        UserEntity findUser = userRepositoryTest.findByUsername("add2");

        assertEquals(findUser, true);


    }
}