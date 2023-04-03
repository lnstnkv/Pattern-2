package ru.tsu.domain.authorization.model

data class AuthModel(
    val clientId:String,
    val grantType:String,
    val username:String,
    val password:String,
)
