package ru.tsu.hits.authservice.security;

import lombok.Data;

@Data
public class RefreshTokenDto {

    private String refreshToken;
}
