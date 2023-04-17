package ru.tsu.data.net.auth.model

import kotlinx.serialization.Serializable
import ru.tsu.domain.authorization.model.UserModel
import ru.tsu.domain.authorization.model.Role
import ru.tsu.domain.authorization.model.StatusRegister

@Serializable
data class RegisterResponseDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val password: String,
    val username: String,
    val role: Role,
    val status: StatusRegister,
) {
    fun toDomain() = UserModel(
        this.id,
        this.firstName,
        this.lastName,
        this.password,
        this.username,
        Role.CLIENT,
        StatusRegister.ACTIVE
    )
}
