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

    @GET("credits/accounts/{userId}")
    suspend fun getCreditByUser( @Path("userId") ownerId: String):List<CreditAccountResponseDto>

    @GET("payments/{accountId}")
    suspend fun getPaymentByAccount( @Path("accountId") accountId: String):List<PaymentsResponseDto>

    @GET("payments/overdue/{accountId}")
    suspend fun getOverdueByAccount( @Path("accountId") accountId: String):List<PaymentsResponseDto>
}