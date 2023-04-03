package ru.tsu.data.net

import ru.tsu.data.net.auth.UserApi
import ru.tsu.data.net.auth.model.toData
import ru.tsu.domain.authorization.UserDataSource
import ru.tsu.domain.authorization.model.RegistrationModel
import ru.tsu.domain.authorization.model.RegistrationToken

class UserDataSourceImpl(private val userApi: UserApi) :
    UserDataSource {
    override suspend fun register(param: RegistrationModel): RegistrationToken {
        val result = userApi.register(param.toData()).toDomain()
        return result

    }
}