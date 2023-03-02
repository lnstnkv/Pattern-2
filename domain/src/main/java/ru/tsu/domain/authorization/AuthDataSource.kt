package ru.tsu.domain.authorization

import ru.tsu.domain.authorization.model.AuthModel
import ru.tsu.domain.authorization.model.AuthToken
import ru.tsu.domain.authorization.model.RegistrationModel
import ru.tsu.domain.authorization.model.RegistrationToken

interface AuthDataSource {
    suspend fun register(param: RegistrationModel): RegistrationToken
    suspend fun login(param: AuthModel): AuthToken
}