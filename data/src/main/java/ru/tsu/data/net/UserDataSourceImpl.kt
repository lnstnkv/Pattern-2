package ru.tsu.data.net

import ru.tsu.data.net.auth.UserApi
import ru.tsu.data.net.auth.model.toData
import ru.tsu.domain.authorization.UserDataSource
import ru.tsu.domain.authorization.model.RegistrationModel
import ru.tsu.domain.authorization.model.UserModel

class UserDataSourceImpl(private val userApi: UserApi) :
    UserDataSource {
    override suspend fun register(param: RegistrationModel): UserModel {
        return userApi.register(param.toData()).toDomain()
    }

    override suspend fun getUsers(): List<UserModel> {
        return userApi.getUsers().map { it.toDomain() }
    }
}