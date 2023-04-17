package ru.tsu.data.net.accounts

import ru.tsu.data.net.operations.AmountMoney
import ru.tsu.domain.account.AccountsDataSource
import ru.tsu.domain.account.model.AccountHistoryModel
import ru.tsu.domain.account.model.AccountModel
import ru.tsu.domain.account.model.AccountsModel
import ru.tsu.domain.account.model.CreateAccountModel
import ru.tsu.domain.account.model.MoneyAmountModel
import ru.tsu.domain.account.model.TransferMoneyModel


class AccountDataSourceImpl(private val accountApi: AccountApi) : AccountsDataSource {
    override suspend fun getListAccounts(ownerId: String, skip: Int?, limit: Int?): AccountsModel {
        return accountApi.getAccounts(ownerId, skip, limit).toDomain()
    }

    override suspend fun deleteAccounts(accountId: String) {
        accountApi.deleteAccount(accountId)
    }

    override suspend fun getAccountHistory(
        accountId: String,
        skip: Int,
        limit: Int
    ): List<AccountHistoryModel> {
        return accountApi.getAccountHistory(accountId, skip, limit)
            .map { history -> history.toDomain() }
    }

    override suspend fun getAccount(accountId: String): AccountModel {
        return (accountApi.getAccount(accountId)).toDomain()
    }

    override suspend fun blockAccount(accountId: String) {
        accountApi.blockAccount(accountId)
    }

    override suspend fun unblockAccount(accountId: String) {
        accountApi.unblockAccount(accountId)
    }

    override suspend fun createAccount(currency: CreateAccountModel): AccountModel {
        return accountApi.createAccount(currency.toData()).toDomain()
    }

}
