package ru.tsu.domain.credits

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import javax.inject.Inject

interface GetListOverduePaymentUseCase : FlowUseCase<String, List<PaymentModel>>

class GetListOverduePaymentUseCaseImpl @Inject constructor(private val dataSource: CreditDataSource) :
    GetListOverduePaymentUseCase {
    override fun execute(param: String): Flow<Result<List<PaymentModel>>> = flow {
        val result = dataSource.getOverduePaymentByAccount(param)
        emit(Result.success(result))
    }
}
