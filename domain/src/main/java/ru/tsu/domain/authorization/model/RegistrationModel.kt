package ru.tsu.domain.authorization.model

data class RegistrationModel(
    val firstName: String,
    val lastName: String,
    val password: String,
    val username: String,
    val role:Role,
    val status:StatusRegister,
)
