package ru.tsu.data.net.accounts.socket

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class HistoryWebSocket(private val json: Json) : WebSocketListener() {

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        Log.d("SOCKET_EXCEPTION", e.stackTraceToString())
    }
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO + exceptionHandler)

    private val _state = MutableStateFlow(SocketState())
    val state: Flow<SocketState> = _state

    private val _messageFlow = MutableSharedFlow<HistorySocketResponse>()
    val messageFlow: Flow<HistorySocketResponse> = _messageFlow

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        _state.update { it.copy(isOpen = true) }
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        _state.update { it.copy(isOpen = false) }
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        try {
            val data = json.decodeFromString<HistorySocketResponse>(text)
            coroutineScope.launch {
                _messageFlow.emit(data)
            }
        } catch (e: Exception) {
            Log.d("SOCKET_EXCEPTION", e.stackTraceToString())
        }
    }

}

data class SocketState(
    val isOpen: Boolean = false,
)
