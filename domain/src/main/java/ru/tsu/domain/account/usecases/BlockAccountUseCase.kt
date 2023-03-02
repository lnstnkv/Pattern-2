package ru.tsu.domain.account.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import ru.tsu.domain.account.AccountsDataSource
import ru.tsu.domain.account.model.AccountId
import javax.inject.Inject

interface BlockAccountUseCase:FlowUseCase<AccountId,Unit>
class BlockAccountUseCaseImpl @Inject constructor(private val dataSource: AccountsDataSource):BlockAccountUseCase {
    override fun execute(param: AccountId): Flow<Result<Unit>> = flow {
        val result = dataSource.blockAccount(param.value)
        emit(Result.success(result))
    }
}