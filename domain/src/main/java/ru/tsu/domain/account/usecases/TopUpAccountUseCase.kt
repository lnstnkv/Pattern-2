package ru.tsu.domain.account.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import ru.tsu.domain.account.AccountsDataSource
import ru.tsu.domain.account.HistoryDataSource
import ru.tsu.domain.account.model.AccountHistoryModelInvariant
import ru.tsu.domain.account.model.MoneyAmountModel
import javax.inject.Inject

interface TopUpAccountUseCase : FlowUseCase<MoneyAmountModel, Unit>
class TopUpAccountUseCaseImpl @Inject constructor(
    private val dataSource: AccountsDataSource,
    private val dao: HistoryDataSource,
) :
    TopUpAccountUseCase {
    override fun execute(param: MoneyAmountModel): Flow<Result<Unit>> = flow {
        val result = dataSource.topUpAccount(param)
        dao.insertHistory(
            AccountHistoryModelInvariant(
                type = "Topup",
                date = getCurrentData(),
                amountOfMoney = param.amountOfMoney,
                accountId = param.accountId,
                ownerId = "1",
            )
        )
        emit(Result.success(result))
    }
}
