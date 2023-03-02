package ru.tsu.domain.account.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import ru.tsu.domain.account.model.AccountModel
import ru.tsu.domain.account.AccountsDataSource
import ru.tsu.domain.account.model.OwnerId
import javax.inject.Inject

interface GetListAccountUseCase : FlowUseCase<OwnerId, List<AccountModel>>

class GetListAccountUseCaseImpl @Inject constructor(private val dataSource: AccountsDataSource) :
    GetListAccountUseCase {
    override fun execute(param: OwnerId): Flow<Result<List<AccountModel>>> = flow {
        val result = dataSource.getListAccounts(param.value, 0, 100).filter {
            !it.isDeleted
        }
        emit(Result.success(result))
    }
}