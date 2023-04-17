package ru.tsu.data.net.accounts.socket

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.tsu.domain.account.AccountHistoryDataSource
import ru.tsu.domain.account.model.AccountHistoryParams
import ru.tsu.domain.account.model.HistoryEvent

class AccountHistoryDataSourceImpl(private val socket: HistoryWebSocketClient) : AccountHistoryDataSource {

    override val messageFlow: Flow<HistoryEvent>
        get() = socket.messageFlow.map { it.toDomain() }
    override val sourceIsActive: Flow<Boolean>
        get() = socket.state.map { it.isOpen }

    override fun send(param: AccountHistoryParams) {
        socket.send(TypeMessageHistorySocket.History, param)
    }
}
