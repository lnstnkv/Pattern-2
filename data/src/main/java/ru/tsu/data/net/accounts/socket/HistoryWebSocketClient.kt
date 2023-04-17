package ru.tsu.data.net.accounts.socket

import android.util.Log
import io.socket.client.IO
import io.socket.emitter.Emitter
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.tsu.domain.account.model.AccountHistoryParams

class HistoryWebSocketClient(
    private val json: Json,
    url: String,
) {

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        Log.d("SOCKET_EXCEPTION", e.stackTraceToString())
    }
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO + exceptionHandler)

    private val _state = MutableStateFlow(SocketState())
    val state: Flow<SocketState> = _state

    private val _messageFlow = MutableSharedFlow<HistorySocketResponse>()
    val messageFlow: Flow<HistorySocketResponse> = _messageFlow

    private val socketListener = Emitter.Listener { args ->
        val data = args[0].toString()
        try {
            val message = json.decodeFromString<HistorySocketResponse>(data)
            coroutineScope.launch {
                _messageFlow.emit(message)
            }
        } catch (e: Exception) {
            Log.d("SOCKET_EXCEPTION", e.stackTraceToString())
        }
    }

    private val socket = IO.socket(url).also { currentSocket ->
        TypeMessageHistorySocket.values().forEach {
            currentSocket.on(it.typeName, socketListener)
        }
        currentSocket.open()
    }

    init {
        coroutineScope.launch {
            while (true) {
                delay(1_000)
                if (!socket.isActive) {
                    socket.open()
                }
            }
        }
        coroutineScope.launch {
            while (true) {
                delay(1_000)
                _state.update { it.copy(isOpen = socket.isActive) }
            }
        }
    }

    fun send(topic: TypeMessageHistorySocket, param: AccountHistoryParams) {
        val dataModel = AccountHistoryParamsDto.fromDomain(param)
        val message = json.encodeToString(dataModel)
        socket.emit(topic.typeName, message)
    }
}


data class SocketState(
    val isOpen: Boolean = false,
)
