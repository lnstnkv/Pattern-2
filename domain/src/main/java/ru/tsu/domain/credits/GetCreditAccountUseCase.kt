package ru.tsu.domain.credits

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import javax.inject.Inject

interface GetCreditAccountUseCase: FlowUseCase<String,List<CreditAccountModel>>

class GetCreditAccountUseCaseImpl @Inject constructor(private val dataSource: CreditDataSource) :
    GetCreditAccountUseCase {
    override fun execute(param: String): Flow<Result<List<CreditAccountModel>>> = flow {
        val result = dataSource.getCreditByUser(param)
        emit(Result.success(result))
    }
}