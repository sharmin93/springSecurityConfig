package com.example.hello_spring.service.db.repo;

import com.example.hello_spring.service.db.entity.TokenManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TokenRepository extends CrudRepository<TokenManager,Long> {
    List<TokenManager> getTokenManagerByLastOneHour(LocalDateTime time);
    @Modifying
    @Query(value = "Update tm set tm.is_active=0 from tbl_token_manager tm where CAST(tm.created_time as DATE)=CAST(GETDATE() as DATE) and tm.user_name=?1",nativeQuery = true)
    int killSession(String userName);
}
