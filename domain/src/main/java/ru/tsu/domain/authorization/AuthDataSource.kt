package ru.tsu.domain.authorization

interface AuthDataSource {
    suspend fun register(param:RegistrationModel):RegistrationToken
    suspend fun login(param:AuthModel):AuthToken
}