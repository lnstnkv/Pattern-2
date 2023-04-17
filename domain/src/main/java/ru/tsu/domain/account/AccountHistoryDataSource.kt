package ru.tsu.domain.account

import kotlinx.coroutines.flow.Flow
import ru.tsu.domain.account.model.AccountHistoryParams
import ru.tsu.domain.account.model.HistoryEvent

interface AccountHistoryDataSource {

    val messageFlow: Flow<HistoryEvent>

    val sourceIsActive: Flow<Boolean>

    fun send(param: AccountHistoryParams)
}
