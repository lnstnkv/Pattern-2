package ru.tsu.data.net.currencies

import retrofit2.http.GET

interface CurrencyApi {

    @GET("currencies")
    suspend fun getCurrencies():List<CurrencyResponseDto>
}