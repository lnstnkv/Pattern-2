package ru.tsu.domain.account.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import ru.tsu.domain.account.model.AccountHistoryModel
import ru.tsu.domain.account.model.AccountId
import ru.tsu.domain.account.AccountsDataSource
import javax.inject.Inject

interface GetAccountHistoryUseCase : FlowUseCase<AccountId, List<AccountHistoryModel>>
class GetAccountHistoryUseCaseImpl @Inject constructor(private val dataSource: AccountsDataSource) :
    GetAccountHistoryUseCase {
    override fun execute(param: AccountId): Flow<Result<List<AccountHistoryModel>>> = flow {
        val result = dataSource.getAccountHistory(param.value,0,100)
        emit(Result.success(result))
    }
}