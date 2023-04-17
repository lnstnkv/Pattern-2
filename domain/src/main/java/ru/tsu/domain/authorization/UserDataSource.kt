package ru.tsu.domain.authorization

import ru.tsu.domain.authorization.model.RegistrationModel
import ru.tsu.domain.authorization.model.UserModel

interface UserDataSource {
    suspend fun register(param: RegistrationModel): UserModel

    suspend fun getUsers(): List<UserModel>
}