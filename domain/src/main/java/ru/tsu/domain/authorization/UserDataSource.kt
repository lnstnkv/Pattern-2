package ru.tsu.domain.authorization

import ru.tsu.domain.authorization.model.RegistrationModel
import ru.tsu.domain.authorization.model.RegistrationToken

interface UserDataSource {
    suspend fun register(param: RegistrationModel): RegistrationToken
}