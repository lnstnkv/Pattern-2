package ru.tsu.domain.account.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import ru.tsu.domain.account.model.AccountModel
import ru.tsu.domain.account.AccountsDataSource
import ru.tsu.domain.account.model.AccountId
import ru.tsu.domain.account.model.OwnerId
import javax.inject.Inject

interface GetAccountUseCase : FlowUseCase<AccountId, AccountModel>
class GetAccountUseCaseImpl @Inject constructor(private val dataSource: AccountsDataSource):
    GetAccountUseCase {
    override fun execute(param: AccountId): Flow<Result<AccountModel>> = flow {
        val result = dataSource.getAccount(param.value)
        emit(Result.success(result))
    }
}