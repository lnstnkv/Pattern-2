package ru.tsu.domain.credits

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import javax.inject.Inject

interface GetCreditsUseCases : FlowUseCase<Unit, List<CreditModel>>
class GetCreditsUseCasesImpl @Inject constructor(private val dataSource: CreditDataSource) :
    GetCreditsUseCases {
    override fun execute(param: Unit): Flow<Result<List<CreditModel>>> = flow {
        val result = dataSource.getCredits()
        emit(Result.success(result))
    }

}