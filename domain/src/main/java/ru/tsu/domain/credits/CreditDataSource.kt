package ru.tsu.domain.credits

interface CreditDataSource {
    suspend fun getCredits():List<CreditModel>
    suspend fun createCredits(creditParamsModel: CreditParamsModel):CreditModel
}