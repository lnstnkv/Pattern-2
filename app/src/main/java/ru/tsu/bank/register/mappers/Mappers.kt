package ru.tsu.bank.register.mappers

import ru.tsu.bank.register.RegisterUiModel
import ru.tsu.domain.authorization.model.UserModel

fun UserModel.toUiModel() = RegisterUiModel(
    id = this.id,
    firstName = this.firstName,
    lastName = this.lastName,
    password = this.password,
    username = this.username,
    role = this.role,
    status = this.status,
)