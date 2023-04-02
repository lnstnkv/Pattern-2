package ru.tsu.data.net.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ParamsRefreshTokenDto(@SerialName("refreshToken") val token: String)
