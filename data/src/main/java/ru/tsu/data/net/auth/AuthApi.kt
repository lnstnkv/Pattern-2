package ru.tsu.data.net.auth

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("register")
     suspend fun register(@Body registerParams: RegisterParamsDto): RegisterResponseDto

    @POST("login")
    suspend fun login(@Body authParams: AuthParamsDto): AuthResponseDto

}