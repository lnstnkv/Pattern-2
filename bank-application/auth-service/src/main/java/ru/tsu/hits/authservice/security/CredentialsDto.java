package ru.tsu.hits.authservice.security;

import lombok.Data;

@Data
public class CredentialsDto {

    private String username;

    private String password;
}
