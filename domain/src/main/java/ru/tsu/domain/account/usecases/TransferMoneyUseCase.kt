package ru.tsu.domain.account.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import ru.tsu.domain.account.AccountsDataSource
import ru.tsu.domain.account.model.TransferMoneyModel
import javax.inject.Inject

interface TransferMoneyUseCase:FlowUseCase<TransferMoneyModel,Unit>
class TransferMoneyUseCaseImpl @Inject constructor(private val dataSource: AccountsDataSource):TransferMoneyUseCase {
    override fun execute(param: TransferMoneyModel): Flow<Result<Unit>> = flow {
        val result= dataSource.transferMoney(param)
        emit(Result.success(result))
    }
}