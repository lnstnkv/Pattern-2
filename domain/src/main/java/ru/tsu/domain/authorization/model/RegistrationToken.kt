package ru.tsu.domain.authorization.model


data class RegistrationToken(
    val id:Int,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val password: String,
    val username: String,
    val role: Role,
    val status: StatusRegister,
)