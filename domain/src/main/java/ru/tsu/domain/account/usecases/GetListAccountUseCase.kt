package ru.tsu.domain.account.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import ru.tsu.domain.account.model.AccountModel
import ru.tsu.domain.account.AccountsDataSource
import ru.tsu.domain.account.model.AccountsModel
import ru.tsu.domain.account.model.OwnerId
import javax.inject.Inject

interface GetListAccountUseCase : FlowUseCase<OwnerId, AccountsModel>

class GetListAccountUseCaseImpl @Inject constructor(private val dataSource: AccountsDataSource) :
    GetListAccountUseCase {
    override fun execute(param: OwnerId): Flow<Result<AccountsModel>> = flow {
        val result = dataSource.getListAccounts(param.value, null, null)
        emit(Result.success(result))
    }
}