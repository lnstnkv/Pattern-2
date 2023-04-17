package ru.tsu.data.net.auth

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import ru.tsu.data.net.auth.model.*

interface AuthApi {

    @POST("auth/access-token")
    suspend fun login(
        @Body authParamsDto: AuthParamsDto
    ): AuthResponseDto

    // TODO: check on backend
    @POST("logout")
    suspend fun logout(): Unit

    // TODO: check on backend
    @POST("auth/refresh-token")
    suspend fun refreshToken(@Body params: ParamsRefreshTokenDto): AuthResponseDto
}
