package ru.tsu.data.net.auth

import retrofit2.http.Body
import retrofit2.http.POST
import ru.tsu.data.net.auth.model.RegisterParamsDto
import ru.tsu.data.net.auth.model.RegisterResponseDto

interface UserApi {
    @POST("users")
    suspend fun register(@Body registerParams: RegisterParamsDto): RegisterResponseDto
}