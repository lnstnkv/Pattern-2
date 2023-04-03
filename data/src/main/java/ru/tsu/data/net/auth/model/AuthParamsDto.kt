package ru.tsu.data.net.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.tsu.domain.authorization.model.AuthModel

@Serializable
data class AuthParamsDto(
    @SerialName("client_id")
    val clientId: String,
    @SerialName("grant_type")
    val grantType: String,
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
)

internal fun AuthModel.toData() = AuthParamsDto(
    password = this.password,
    username = this.username,
    grantType = this.grantType,
    clientId = this.clientId,
)
