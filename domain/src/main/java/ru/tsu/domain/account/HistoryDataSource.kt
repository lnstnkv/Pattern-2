package ru.tsu.domain.account

import kotlinx.coroutines.flow.Flow
import ru.tsu.domain.account.model.AccountHistoryModelInvariant

interface HistoryDataSource {
    fun getHistory(): Flow<List<AccountHistoryModelInvariant>>
    fun insertHistory(historyModel: AccountHistoryModelInvariant)
}
