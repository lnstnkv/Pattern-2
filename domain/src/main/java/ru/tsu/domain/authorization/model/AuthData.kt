package ru.tsu.domain.authorization.model

data class AuthData(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn:Int,
    val refreshTokenExpiresIn:Int,
    val tokenType:String,
)
