package ru.tsu.domain.operations.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import ru.tsu.domain.account.HistoryDataSource
import ru.tsu.domain.account.model.AccountHistoryModelInvariant
import ru.tsu.domain.account.model.MoneyAmountModel
import ru.tsu.domain.account.usecases.getCurrentData
import ru.tsu.domain.operations.OperationsDataSource
import javax.inject.Inject

interface WithdrawMoneyFromAccountUseCase : FlowUseCase<MoneyAmountModel, Unit>
class WithdrawMoneyFromAccountUseCaseImpl @Inject constructor(
    private val dataSource: OperationsDataSource,
    private val dao: HistoryDataSource,
) :
    WithdrawMoneyFromAccountUseCase {
    override fun execute(param: MoneyAmountModel): Flow<Result<Unit>> = flow {
        val result = dataSource.withdrawMoneyFromAccount(param)
        dao.insertHistory(
            AccountHistoryModelInvariant(
                type = "Withdraw",
                date = getCurrentData(),
                amountOfMoney = param.amountOfMoney,
                accountId = param.accountId,
                ownerId = "1"
            )
        )
        emit(Result.success(result))
    }
}
