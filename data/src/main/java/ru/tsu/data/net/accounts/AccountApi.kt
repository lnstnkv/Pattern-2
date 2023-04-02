package ru.tsu.data.net.accounts

import retrofit2.http.*

interface AccountApi {
    @GET("users/{id}/accounts")
    suspend fun getAccounts(
        @Path("id") ownerId: String,
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): AccountsResponseDto

    @DELETE("accounts/{id}")
    suspend fun deleteAccount(@Path("id") accountId: String)

    @GET("accounts/{id}/history")
    suspend fun getAccountHistory(
        @Path("id") accountId: String, @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): List<AccountHistoryResponseDto>

    @GET("accounts/{id}")
    suspend fun getAccount(@Path("id") accountId: String): AccountResponseDto

    @POST("accounts/{id}/withdraw")
    suspend fun withdrawMoneyFromAccount(
        @Path("id") accountId: String,
        @Body amountMoney: AmountMoney
    )

    @POST("accounts/{id}/topUp")
    suspend fun topUpAccount(
        @Path("id") accountId: String,
        @Body amountMoney: AmountMoney
    )

    @POST("accounts/{id}/transfer/{receiverId}")
    suspend fun transferMoney(
        @Path("id") accountId: String,
        @Path("receiverId") receiverId: String,
        @Body amountMoney: AmountMoney
    )

    @POST("accounts/{id}/block")
    suspend fun blockAccount(@Path("id") accountId: String)

    @POST("accounts/{id}/unblock")
    suspend fun unblockAccount(@Path("id") accountId: String)

    @POST("accounts")
    suspend fun createAccount(@Body account: AccountDto):AccountResponseDto
}