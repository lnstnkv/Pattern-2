package ru.tsu.bank.register

import ru.tsu.domain.authorization.model.Role
import ru.tsu.domain.authorization.model.StatusRegister

data class RegisterUiModel(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val password: String,
    val username: String,
    val role: Role,
    val status: StatusRegister,
)