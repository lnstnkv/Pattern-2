package ru.tsu.domain.authorization.model


data class UserModel(
    val id:Int,
    val firstName: String,
    val lastName: String,
    val password: String,
    val username: String,
    val role: Role,
    val status: StatusRegister,
)