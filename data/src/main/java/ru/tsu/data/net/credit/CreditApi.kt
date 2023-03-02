package ru.tsu.data.net.credit

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CreditApi {
    @POST("credits")
    suspend fun createCredit(@Body creditParamsDto: CreditParamsDto):CreditResponseDto

    @GET("credits")
    suspend fun getCredits():List<CreditResponseDto>
}