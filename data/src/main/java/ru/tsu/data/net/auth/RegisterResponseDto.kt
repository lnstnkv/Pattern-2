package ru.tsu.data.net.auth

import kotlinx.serialization.Serializable
import ru.tsu.domain.authorization.model.RegistrationToken
import ru.tsu.domain.authorization.model.Role
import ru.tsu.domain.authorization.model.StatusRegister

@Serializable
data class RegisterResponseDto(
    val id: Int,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val password: String,
    val username: String,
    val role: Role,
    val status: StatusRegister,
) {
    fun toDomain() = RegistrationToken(
        this.id,
        this.firstName,
        this.middleName,
        this.lastName,
        this.password,
        this.username,
        Role.CLIENT,
        StatusRegister.ACTIVE
    )
}