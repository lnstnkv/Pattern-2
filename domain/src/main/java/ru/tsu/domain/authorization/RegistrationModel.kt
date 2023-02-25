package ru.tsu.domain.authorization

data class RegistrationModel(
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val password: String,
    val username: String
)
