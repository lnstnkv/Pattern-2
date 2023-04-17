package ru.tsu.data.net.credit

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CreditApi {
    @POST("credits")
    suspend fun createCredit(@Body creditParamsDto: CreditParamsDto):CreditResponseDto

    @GET("credits")
    suspend fun getCredits():List<CreditResponseDto>

    @GET("ratings/{userId}")
    suspend fun getCreditRating( @Path("userId") ownerId: String):RatingResponse
}