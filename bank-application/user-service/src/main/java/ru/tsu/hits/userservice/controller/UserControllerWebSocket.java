package ru.tsu.hits.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.tsu.hits.userservice.dto.CreateUpdateUserRequest;
import ru.tsu.hits.userservice.dto.UserIdRequest;
import ru.tsu.hits.userservice.entity.UserEntity;
import ru.tsu.hits.userservice.service.UserService;

import java.util.List;

@Controller
public class UserControllerWebSocket {

    private final UserService service;

    @Autowired
    public UserControllerWebSocket(UserService service) {
        this.service = service;
    }

    @MessageMapping("/get-all")
    @SendTo("/userServiceTopic/all-users")
    public List<UserEntity> getAllUsers() {
        return service.get();
    }

    @MessageMapping("/get-user")
    @SendTo("/userServiceTopic/get-user-data")
    public UserEntity getUserData(UserIdRequest id) {
        return service.getUser(id.getId());
    }

    @MessageMapping("/create-user")
    @SendTo("/userServiceTopic/create-user")
    public UserEntity createUser(CreateUpdateUserRequest request) {
        return service.createUser(request);
    }

    @MessageMapping("/update-user")
    @SendTo("/userServiceTopic/update-user")
    public UserEntity updateUser(UserEntity entity) {
        return service.updateUser(entity.getId(), map(entity));
    }

    @MessageMapping("/delete-user")
    @SendTo("/userServiceTopic/delete-user")
    public void updateUser(UserIdRequest request) {
        service.deleteUser(request.getId());
    }

    private CreateUpdateUserRequest map(UserEntity entity) {
        CreateUpdateUserRequest request = new CreateUpdateUserRequest();
        request.setRole(entity.getRole());
        request.setUsername(entity.getUsername());
        request.setPassword(entity.getPassword());
        request.setLastName(entity.getLastName());
        request.setFirstName(entity.getFirstName());
        request.setStatus(entity.getStatus());
        return request;
    }
}
