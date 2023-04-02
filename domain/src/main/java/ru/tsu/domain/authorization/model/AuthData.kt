package ru.tsu.domain.authorization.model

data class AuthData(
    val accessToken: String,
    val refreshToken: String,
)
