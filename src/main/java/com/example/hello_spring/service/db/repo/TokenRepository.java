package com.example.hello_spring.service.db.repo;

import com.example.hello_spring.service.db.entity.TokenManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TokenRepository extends CrudRepository<TokenManager,Long> {
    List<TokenManager> getTokenManagerByLastOneHour(LocalDateTime time);
}
