package ru.tsu.data.net.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.tsu.domain.authorization.model.AuthToken

@Serializable
data class AuthResponseDto(
    @SerialName("token")
    val token: String,
) {
    fun toDomain() = AuthToken(token)
}