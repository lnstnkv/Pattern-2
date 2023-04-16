package ru.tsu.hits.userservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.userservice.dto.CreateUpdateUserRequest;
import ru.tsu.hits.userservice.entity.UserEntity;
import ru.tsu.hits.userservice.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public List<UserEntity> getUsers() {
        return service.get();
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public UserEntity getUser(@PathVariable String id) {
        return service.getUser(Integer.parseInt(id));
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public UserEntity createUser(@RequestBody @Valid CreateUpdateUserRequest request) {
        return service.createUser(request);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public UserEntity updateUser(
            @PathVariable int id,
            @RequestBody @Valid CreateUpdateUserRequest request
    ) {

        return service.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public void deleteUser(@PathVariable int id) {
        service.deleteUser(id);
    }
}
