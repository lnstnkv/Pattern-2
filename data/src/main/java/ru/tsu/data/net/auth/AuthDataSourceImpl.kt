package ru.tsu.data.net.auth

import ru.tsu.data.net.accounts.AccountApi
import ru.tsu.data.net.accounts.AccountDto
import ru.tsu.domain.authorization.*
import ru.tsu.domain.authorization.model.AuthModel
import ru.tsu.domain.authorization.model.AuthToken
import ru.tsu.domain.authorization.model.RegistrationModel
import ru.tsu.domain.authorization.model.RegistrationToken

class AuthDataSourceImpl(private val authApi: AuthApi,private val accountApi: AccountApi) : AuthDataSource {
    override suspend fun register(param: RegistrationModel): RegistrationToken {
        val result = authApi.register(param.toData()).toDomain()
        accountApi.createAccount(AccountDto("string",result.id.toString()))
        return result
    }

    override suspend fun login(param: AuthModel): AuthToken {
        return authApi.login(param.toData()).toDomain()
    }
}