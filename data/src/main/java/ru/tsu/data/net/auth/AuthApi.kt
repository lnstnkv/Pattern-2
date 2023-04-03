package ru.tsu.data.net.auth

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import ru.tsu.data.net.auth.model.AuthResponseDto
import ru.tsu.data.net.auth.model.ParamsRefreshTokenDto
import ru.tsu.data.net.auth.model.RegisterParamsDto
import ru.tsu.data.net.auth.model.RegisterResponseDto

interface AuthApi {

    @POST("token")
    @FormUrlEncoded
    suspend fun login(
        @Field("client_id") clientId: String,
        @Field("grant_type") grantType: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): AuthResponseDto

    // TODO: check on backend
    @POST("logout")
    suspend fun logout(): Unit

    // TODO: check on backend
    @POST("refreshToken")
    suspend fun refreshToken(@Body params: ParamsRefreshTokenDto): AuthResponseDto
}
