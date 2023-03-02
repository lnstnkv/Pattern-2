package ru.tsu.data.net.credit

import ru.tsu.domain.credits.CreditDataSource
import ru.tsu.domain.credits.CreditModel
import ru.tsu.domain.credits.CreditParamsModel

class CreditDataSourceImpl(private val creditApi: CreditApi) : CreditDataSource {
    override suspend fun getCredits(): List<CreditModel> {
        return creditApi.getCredits().map { creditResponseDto -> creditResponseDto.toDomain() }
    }

    override suspend fun createCredits(creditParamsModel: CreditParamsModel): CreditModel {
        return creditApi.createCredit(creditParamsModel.toData()).toDomain()
    }
}