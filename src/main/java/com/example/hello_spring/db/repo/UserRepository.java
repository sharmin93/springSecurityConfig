package com.example.hello_spring.db.repo;
import com.example.hello_spring.db.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;




public interface UserRepository extends CrudRepository<UserEntity, Long> {

   UserEntity findByUsername(String username);

}
