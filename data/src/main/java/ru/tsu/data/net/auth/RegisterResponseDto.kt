package ru.tsu.data.net.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.tsu.domain.authorization.model.RegistrationToken

@Serializable
data class RegisterResponseDto(
    @SerialName("token")
    val token:String
){
    fun toDomain()= RegistrationToken(token)
}