package ru.tsu.hits.userservice.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.userservice.dto.CreateUpdateUserRequest;
import ru.tsu.hits.userservice.entity.UserEntity;
import ru.tsu.hits.userservice.exception.user.UnauthorizedUserException;
import ru.tsu.hits.userservice.service.UserService;

import java.security.Principal;
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
    public UserEntity getUser(@PathVariable String id, Principal principal) throws UnauthorizedUserException {
        return service.getUser(Integer.parseInt(id), principal.getName());
    }

    @PostMapping
    public UserEntity createUser(@RequestBody @Valid CreateUpdateUserRequest request) {
        return service.createUser(request);
    }

    @PutMapping("/{id}")
    public UserEntity updateUser(
            @PathVariable int id,
            @RequestBody @Valid CreateUpdateUserRequest request,
            Principal principal
    ) throws UnauthorizedUserException {
        return service.updateUser(id, request, principal.getName());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        service.deleteUser(id);
    }
}
