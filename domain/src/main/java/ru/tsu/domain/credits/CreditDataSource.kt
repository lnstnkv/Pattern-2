package ru.tsu.domain.credits

interface CreditDataSource {
    suspend fun getCredits():List<CreditModel>
    suspend fun getRating(userId:String):RatingModel
    suspend fun createCredits(creditParamsModel: CreditParamsModel):CreditAccountModel
    suspend fun getCreditByUser(userId: String):List<CreditAccountModel>
    suspend fun getPaymentByAccount(account:String):List<PaymentModel>

    suspend fun getOverduePaymentByAccount(account:String):List<PaymentModel>


}