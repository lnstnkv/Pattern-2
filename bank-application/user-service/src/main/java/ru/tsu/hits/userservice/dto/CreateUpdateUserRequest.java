package ru.tsu.hits.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.tsu.hits.userservice.entity.Role;
import ru.tsu.hits.userservice.entity.Status;

@Data
public class CreateUpdateUserRequest {

    @NotBlank(message = "Поле не должно быть пустым")
    private String firstName;

    @NotBlank(message = "Поле не должно быть пустым")
    private String lastName;

    @NotBlank(message = "Поле не должно быть пустым")
    private String username;

    private String password;

    private Role role;

    private Status status;
}
