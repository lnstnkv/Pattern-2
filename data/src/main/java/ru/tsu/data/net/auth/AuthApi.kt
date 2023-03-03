package ru.tsu.data.net.auth

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("users")
    suspend fun register(@Body registerParams: RegisterParamsDto): RegisterResponseDto

    @POST("users")
    suspend fun login(@Body authParams: AuthParamsDto): AuthResponseDto

}