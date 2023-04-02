package ru.tsu.data.net.auth

import retrofit2.http.Body
import retrofit2.http.POST
import ru.tsu.data.net.auth.model.AuthParamsDto
import ru.tsu.data.net.auth.model.AuthResponseDto
import ru.tsu.data.net.auth.model.ParamsRefreshTokenDto
import ru.tsu.data.net.auth.model.RegisterParamsDto
import ru.tsu.data.net.auth.model.RegisterResponseDto

interface AuthApi {
    @POST("users")
    suspend fun register(@Body registerParams: RegisterParamsDto): RegisterResponseDto

    @POST("users")
    suspend fun login(@Body authParams: AuthParamsDto): AuthResponseDto

    // TODO: check on backend
    @POST("logout")
    suspend fun logout(): Unit

    // TODO: check on backend
    @POST("refreshToken")
    suspend fun refreshToken(@Body params: ParamsRefreshTokenDto): AuthResponseDto
}
