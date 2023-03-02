package ru.tsu.domain.account.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import ru.tsu.domain.account.AccountsDataSource
import ru.tsu.domain.account.model.MoneyAmountModel
import javax.inject.Inject

interface TopUpAccountUseCase : FlowUseCase<MoneyAmountModel, Unit>
class TopUpAccountUseCaseImpl @Inject constructor(private val dataSource: AccountsDataSource):
    TopUpAccountUseCase {
    override fun execute(param: MoneyAmountModel): Flow<Result<Unit>> = flow {
        val result = dataSource.topUpAccount(param)
        emit(Result.success(result))
    }
}