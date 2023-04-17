package ru.tsu.data.net.operations

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface OperationsApi {
    @POST("operations/{id}/withdraw")
    suspend fun withdrawMoneyFromAccount(
        @Path("id") accountId: String,
        @Body amountMoney: AmountMoney
    )

    @POST("operations/{id}/topUp")
    suspend fun topUpAccount(
        @Path("id") accountId: String,
        @Body amountMoney: AmountMoney
    )

    @POST("operations/{id}/transfer/{receiverId}")
    suspend fun transferMoney(
        @Path("id") accountId: String,
        @Path("receiverId") receiverId: String,
        @Body amountMoney: AmountMoney
    )
}