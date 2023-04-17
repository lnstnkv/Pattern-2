package ru.tsu.data.net.operations

import ru.tsu.data.net.accounts.AccountApi
import ru.tsu.domain.account.AccountsDataSource
import ru.tsu.domain.account.model.MoneyAmountModel
import ru.tsu.domain.account.model.TransferMoneyModel
import ru.tsu.domain.operations.OperationsDataSource

class OperationsDataSourceImpl(private val operationsApi: OperationsApi) : OperationsDataSource {

    override suspend fun withdrawMoneyFromAccount(param: MoneyAmountModel) {
        operationsApi.withdrawMoneyFromAccount(param.accountId, AmountMoney(param.amountOfMoney))
    }

    override suspend fun topUpAccount(param: MoneyAmountModel) {
        operationsApi.topUpAccount(param.accountId, AmountMoney(param.amountOfMoney))
    }

    override suspend fun transferMoney(param: TransferMoneyModel) {
        operationsApi.transferMoney(param.accountId,param.receiverId, AmountMoney(param.amountOfMoney))
    }
}