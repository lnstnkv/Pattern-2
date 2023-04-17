package ru.tsu.domain.operations

import ru.tsu.domain.account.model.MoneyAmountModel
import ru.tsu.domain.account.model.TransferMoneyModel

interface OperationsDataSource {
    suspend fun withdrawMoneyFromAccount(param: MoneyAmountModel)
    suspend fun topUpAccount(param: MoneyAmountModel)
    suspend fun transferMoney(param: TransferMoneyModel)
}