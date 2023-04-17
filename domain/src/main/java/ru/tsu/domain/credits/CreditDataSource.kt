package ru.tsu.domain.credits

interface CreditDataSource {
    suspend fun getCredits():List<CreditModel>

    suspend fun getRating(userId:String):RatingModel
    suspend fun createCredits(creditParamsModel: CreditParamsModel):CreditModel
}