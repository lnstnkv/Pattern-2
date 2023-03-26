package ru.tsu.hits.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.userservice.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUsername(String username);
}