package ru.tsu.domain.account.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import ru.tsu.domain.account.model.AccountId
import ru.tsu.domain.account.AccountsDataSource
import javax.inject.Inject

interface DeleteAccountUseCase : FlowUseCase<AccountId,Unit>
class DeleteAccountUseCaseImpl @Inject constructor(private val dataSource: AccountsDataSource) :
    DeleteAccountUseCase {
    override fun execute(param: AccountId): Flow<Result<Unit>> = flow {
        val result = dataSource.deleteAccounts(param.value)
        emit(Result.success(result))
    }
}