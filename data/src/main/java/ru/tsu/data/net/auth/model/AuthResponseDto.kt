package ru.tsu.data.net.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.tsu.domain.authorization.model.AuthData

@Serializable
data class AuthResponseDto(
    // TODO: check on backend
    @SerialName("access_token")
    val accessToken: String,
    // TODO: check on backend
    @SerialName("refresh_token")
    val refreshToken: String,
) {
    fun toDomain() = AuthData(accessToken = accessToken, refreshToken = refreshToken)
}
