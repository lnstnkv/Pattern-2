package ru.tsu.domain.authorization

import ru.tsu.domain.authorization.model.Role
import ru.tsu.domain.authorization.model.StatusRegister


data class RegistrationToken(
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val password: String,
    val username: String,
    val role: Role,
    val status: StatusRegister,
)