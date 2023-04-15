package ru.tsu.hits.userservice.service;

import org.springframework.stereotype.Service;
import ru.tsu.hits.userservice.dto.CreateUpdateUserRequest;
import ru.tsu.hits.userservice.entity.UserEntity;
import ru.tsu.hits.userservice.exception.user.UserNotFoundException;
import ru.tsu.hits.userservice.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserEntity> get() {
        return repository.findAll();
    }

    public UserEntity getUser(Integer id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь не найден."));
    }

    public UserEntity createUser(CreateUpdateUserRequest request) {
        UserEntity user = map(request);
        user = repository.save(user);
        return user;
    }

    public UserEntity updateUser(int id, CreateUpdateUserRequest request) {
        UserEntity user = repository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Пользователь не найден.")
        );
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword() != null ? request.getPassword() : user.getPassword());
        user.setRole(request.getRole() != null ? request.getRole() : user.getRole());
        user.setStatus(request.getStatus() != null ? request.getStatus() : user.getStatus());
        repository.flush();
        return user;
    }

    public void deleteUser(int id) {
        repository.deleteById(id);
    }

    public UserEntity map(CreateUpdateUserRequest request) {
        UserEntity existing = repository.findByUsername(request.getUsername());
        if (existing != null) {
            throw new IllegalArgumentException("Пользователь с подобным username уже существует.");
        }
        UserEntity entity = new UserEntity();
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setUsername(request.getUsername());
        entity.setPassword(request.getPassword());
        entity.setRole(request.getRole());
        entity.setStatus(request.getStatus());
        return entity;
    }
}
