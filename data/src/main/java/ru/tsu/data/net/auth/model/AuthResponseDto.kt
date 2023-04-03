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
    @SerialName("expires_in")
    val expiresIn: Int,
    @SerialName("refresh_expires_in")
    val refreshTokenExpiresIn: Int,
    @SerialName("token_type")
    val tokenType: String,
    @SerialName("not-before-policy")
    val policy: Int,
    @SerialName("session_state")
    val sessionState: String,
    @SerialName("scope")
    val scope: String,

    ) {
    fun toDomain() = AuthData(
        accessToken = accessToken, refreshToken = refreshToken,
        expiresIn = expiresIn,
        refreshTokenExpiresIn = refreshTokenExpiresIn,
        tokenType = tokenType,
        policy = policy,
        sessionState = sessionState,
        scope = scope
    )
}
