package ru.tsu.domain.account.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import ru.tsu.domain.account.AccountsDataSource
import ru.tsu.domain.account.HistoryDataSource
import ru.tsu.domain.account.model.AccountHistoryModelInvariant
import ru.tsu.domain.account.model.AccountId
import javax.inject.Inject

interface GetAccountHistoryUseCase : FlowUseCase<AccountId, List<AccountHistoryModelInvariant>>
class GetAccountHistoryUseCaseImpl @Inject constructor(
    private val dataSource: AccountsDataSource,
    private val reactiveDataSource: HistoryDataSource,
) :
    GetAccountHistoryUseCase {
    override fun execute(param: AccountId): Flow<Result<List<AccountHistoryModelInvariant>>> = flow {
        // val result = dataSource.getAccountHistory(param.value,0,100)
        // emit(Result.success(result))
        reactiveDataSource.getHistory().collect {
            emit(Result.success(it))
        }
    }
}
