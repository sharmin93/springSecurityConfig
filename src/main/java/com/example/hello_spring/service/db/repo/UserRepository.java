package com.example.hello_spring.service.db.repo;
import com.example.hello_spring.service.db.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;




public interface UserRepository extends CrudRepository<UserEntity, Long> {

   UserEntity findByUsername(String username);

   UserEntity findFirstById(long id);
}
