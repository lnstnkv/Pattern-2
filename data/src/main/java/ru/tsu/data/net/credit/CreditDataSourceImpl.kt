package ru.tsu.data.net.credit

import ru.tsu.domain.credits.*

class CreditDataSourceImpl(private val creditApi: CreditApi) : CreditDataSource {
    override suspend fun getCredits(): List<CreditModel> {
        return creditApi.getCredits().map { creditResponseDto -> creditResponseDto.toDomain() }
    }

    override suspend fun getRating(userId: String): RatingModel {
        return creditApi.getCreditRating(userId).toDomain()
    }

    override suspend fun createCredits(creditParamsModel: CreditParamsModel): CreditModel {
        return creditApi.createCredit(creditParamsModel.toData()).toDomain()
    }

    override suspend fun getCreditByUser(userId: String): List<CreditAccountModel> {
        return creditApi.getCreditByUser(userId).map { it.toDomain() }
    }

    override suspend fun getPaymentByAccount(account: String): List<PaymentModel> {
        return creditApi.getPaymentByAccount(account).map { it.toDomain() }
    }

    override suspend fun getOverduePaymentByAccount(account: String): List<PaymentModel> {
        return creditApi.getOverdueByAccount(account).map { it.toDomain() }
    }
}