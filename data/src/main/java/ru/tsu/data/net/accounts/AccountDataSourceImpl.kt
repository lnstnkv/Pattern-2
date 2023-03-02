package ru.tsu.data.net.accounts

import ru.tsu.domain.account.AccountsDataSource
import ru.tsu.domain.account.model.*


class AccountDataSourceImpl(private val accountApi: AccountApi) : AccountsDataSource {
    override suspend fun getListAccounts(
        ownerId: String, skip: Int, limit: Int
    ): List<AccountModel> {
        return accountApi.getAccounts(ownerId, skip, limit).map { account -> account.toDomain() }
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

    override suspend fun withdrawMoneyFromAccount(param: MoneyAmountModel) {
        accountApi.withdrawMoneyFromAccount(param.accountId, AmountMoney(param.amountOfMoney))
    }

    override suspend fun topUpAccount(param: MoneyAmountModel) {
        accountApi.topUpAccount(param.accountId,AmountMoney(param.amountOfMoney))
    }

    override suspend fun transferMoney(param: TransferMoneyModel) {
        accountApi.transferMoney(param.accountId,param.receiverId, AmountMoney(param.amountOfMoney))
    }

    override suspend fun blockAccount(accountId: String) {
        accountApi.blockAccount(accountId)
    }

    override suspend fun unblockAccount(accountId: String) {
        accountApi.unblockAccount(accountId)
    }

    override suspend fun createAccount(currency: CurrencyModel): AccountModel {
       return accountApi.createAccount(currency.toData()).toDomain()
    }

}