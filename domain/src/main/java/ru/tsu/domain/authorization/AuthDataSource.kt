package ru.tsu.domain.authorization

import ru.tsu.domain.authorization.model.AuthData
import ru.tsu.domain.authorization.model.AuthModel

interface AuthDataSource {
    // suspend fun register(param: RegistrationModel): RegistrationToken
    suspend fun login(param: AuthModel): AuthData
}
