package ru.tsu.data.net.auth

import ru.tsu.domain.authorization.*
import ru.tsu.domain.authorization.model.AuthModel
import ru.tsu.domain.authorization.model.AuthToken
import ru.tsu.domain.authorization.model.RegistrationModel
import ru.tsu.domain.authorization.model.RegistrationToken

class AuthDataSourceImpl(private val authApi: AuthApi) : AuthDataSource {
    override suspend fun register(param: RegistrationModel): RegistrationToken {
        return authApi.register(param.toData()).toDomain()
    }

    override suspend fun login(param: AuthModel): AuthToken {
        return authApi.login(param.toData()).toDomain()
    }
}