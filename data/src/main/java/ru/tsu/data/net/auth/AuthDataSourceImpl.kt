package ru.tsu.data.net.auth

import ru.tsu.data.net.accounts.AccountApi
import ru.tsu.data.net.accounts.AccountDto
import ru.tsu.data.net.auth.model.toData
import ru.tsu.domain.authorization.AuthDataSource
import ru.tsu.domain.authorization.model.AuthData
import ru.tsu.domain.authorization.model.AuthModel
import ru.tsu.domain.authorization.model.RegistrationModel
import ru.tsu.domain.authorization.model.RegistrationToken

class AuthDataSourceImpl(private val authApi: AuthApi, private val userApi: UserApi,private val accountApi: AccountApi) :
    AuthDataSource {
       override suspend fun login(param: AuthModel): AuthData {
        return authApi.login(param.toData()).toDomain()

    }
}
