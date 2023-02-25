package ru.tsu.data.net.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.tsu.domain.authorization.AuthModel


@Serializable
data class AuthParamsDto(
    @SerialName("username")
    val username:String,
    @SerialName("password")
    val password:String,
)
internal fun AuthModel.toData()=AuthParamsDto(
    password = this.password,
    username = this.username
)
