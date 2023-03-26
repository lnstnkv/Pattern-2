package ru.tsu.hits.userservice.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.userservice.dto.CreateUpdateUserRequest;
import ru.tsu.hits.userservice.entity.UserEntity;
import ru.tsu.hits.userservice.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserEntity> getUsers() {
        return service.get();
    }

    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable String id) {
        return service.getUser(Integer.parseInt(id));
    }

    @PostMapping
    public UserEntity createUser(@RequestBody @Valid CreateUpdateUserRequest request) {
        return service.createUser(request);
    }

    @PutMapping("/{id}")
    public UserEntity updateUser(@PathVariable int id, @RequestBody @Valid CreateUpdateUserRequest request) {
        return service.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        service.deleteUser(id);
    }
}
