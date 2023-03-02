package ru.tsu.domain.account

import ru.tsu.domain.account.model.*

interface AccountsDataSource {
    suspend fun getListAccounts(ownerId:String, skip:Int, limit:Int): List<AccountModel>
    suspend fun deleteAccounts(accountId:String)
    suspend fun getAccountHistory(accountId: String, skip:Int, limit:Int):List<AccountHistoryModel>
    suspend fun getAccount(accountId: String): AccountModel
    suspend fun withdrawMoneyFromAccount(param: MoneyAmountModel)
    suspend fun topUpAccount(param: MoneyAmountModel)
    suspend fun transferMoney(param:TransferMoneyModel)
    suspend fun blockAccount(accountId: String)
    suspend fun unblockAccount(accountId: String)
    suspend fun createAccount(currency: CurrencyModel):AccountModel
}