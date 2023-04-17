package ru.tsu.data.net.accounts.socket

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.tsu.domain.account.AccountHistoryDataSource
import ru.tsu.domain.account.model.AccountHistoryParams
import ru.tsu.domain.account.model.HistoryEvent

class AccountHistoryDataSourceImpl(
    private val socket: HistoryWebSocket,
    private val request: Request,
    private val json: Json,
) : AccountHistoryDataSource {

    private val okHttpClient = OkHttpClient()
    private val webSocket = okHttpClient.newWebSocket(request, socket)

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        Log.d("HISTORY_DATA_SOURCE_EXCEPTION", e.stackTraceToString())
    }
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO + exceptionHandler)

    init {
        socket.state
            .onEach {
                if (!it.isOpen) {
                    okHttpClient.connectionPool.evictAll()
                }
            }
            .launchIn(coroutineScope)
    }

    override val messageFlow: Flow<HistoryEvent>
        get() = socket.messageFlow.map { it.toDomain() }
    override val sourceIsActive: Flow<Boolean>
        get() = socket.state.map { it.isOpen }

    override fun send(param: AccountHistoryParams) {
        val dataModel = AccountHistoryParamsDto.fromDomain(param)
        val message = json.encodeToString(dataModel)
        webSocket.send(message)
    }
}
