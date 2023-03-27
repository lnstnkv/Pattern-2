package ru.tsu.domain.account.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import ru.tsu.domain.account.AccountsDataSource
import ru.tsu.domain.account.model.AccountModel
import ru.tsu.domain.account.model.CreateAccountModel
import javax.inject.Inject

interface CreateAccountUseCase : FlowUseCase<CreateAccountModel, AccountModel>
class CreateAccountUseCaseImpl @Inject constructor(private val dataSource: AccountsDataSource) :
    CreateAccountUseCase {
    override fun execute(param: CreateAccountModel): Flow<Result<AccountModel>> = flow {
        val result = dataSource.createAccount(param)
        emit(Result.success(result))
    }

}