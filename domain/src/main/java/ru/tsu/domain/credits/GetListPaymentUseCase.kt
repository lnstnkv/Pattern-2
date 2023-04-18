package ru.tsu.domain.credits

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import javax.inject.Inject

interface GetListPaymentUseCase:FlowUseCase<String,List<PaymentModel>>

class GetListPaymentUseCaseImpl@Inject constructor(private val dataSource: CreditDataSource) :
    GetListPaymentUseCase {
    override fun execute(param: String): Flow<Result<List<PaymentModel>>> =flow {
        val result = dataSource.getPaymentByAccount(param)
        emit(Result.success(result))
    }
}
