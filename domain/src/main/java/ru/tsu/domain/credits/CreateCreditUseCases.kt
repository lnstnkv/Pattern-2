package ru.tsu.domain.credits

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import javax.inject.Inject

interface CreateCreditUseCases : FlowUseCase<CreditParamsModel, CreditModel>
class CreateCreditUseCasesImpl @Inject constructor(private val dataSource: CreditDataSource) :
    CreateCreditUseCases {
    override fun execute(param: CreditParamsModel): Flow<Result<CreditModel>> = flow {
        val result = dataSource.createCredits(param)
        emit(Result.success(result))
    }

}