package ru.tsu.domain.account

import ru.tsu.domain.account.model.AccountModel
import ru.tsu.domain.account.model.AccountsModel
import ru.tsu.domain.account.model.CreateAccountModel
import ru.tsu.domain.account.model.HistoryEvent

interface AccountsDataSource {
    suspend fun getListAccounts(ownerId:String, skip:Int?, limit:Int?): AccountsModel
    suspend fun deleteAccounts(accountId: String)
    suspend fun getAccountHistory(accountId: String, skip: Int, limit: Int): List<HistoryEvent.HistoryModel>
    suspend fun getAccount(accountId: String): AccountModel
       suspend fun blockAccount(accountId: String)
    suspend fun unblockAccount(accountId: String)
    suspend fun createAccount(currency: CreateAccountModel):AccountModel
}
