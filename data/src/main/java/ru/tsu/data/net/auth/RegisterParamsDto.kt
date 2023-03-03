package ru.tsu.data.net.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.tsu.domain.authorization.model.RegistrationModel

@Serializable
data class RegisterParamsDto(
    @SerialName("firstName")
    val firstName:String,
    @SerialName("middleName")
    val middleName:String,
    @SerialName("lastName")
    val lastName:String,
    @SerialName("password")
    val password:String,
    @SerialName("username")
    val username:String,
    @SerialName("role")
    val role:Role,
    @SerialName("status")
    val status:StatusRegister
)
internal fun RegistrationModel.toData()=RegisterParamsDto(
    firstName = this.firstName,
    middleName = this.middleName,
    lastName = this.lastName,
    password = this.password,
    username = this.username,
    role = Role.CLIENT,
    status = StatusRegister.ACTIVE,

)
