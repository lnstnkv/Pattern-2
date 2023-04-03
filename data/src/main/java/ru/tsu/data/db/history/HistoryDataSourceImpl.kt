package ru.tsu.data.db.history

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.tsu.domain.account.HistoryDataSource
import ru.tsu.domain.account.model.AccountHistoryModelInvariant

class HistoryDataSourceImpl(
    private val dao: HistoryDao,
) : HistoryDataSource {

    override fun getHistory(): Flow<List<AccountHistoryModelInvariant>> = dao.getHistory().map { list ->
        list.map { account -> account.toDomain() }
    }

    override fun insertHistory(historyModel: AccountHistoryModelInvariant) {
        dao.insertHistory(entity = HistoryEntity.fromDomain(historyModel))
    }
}
