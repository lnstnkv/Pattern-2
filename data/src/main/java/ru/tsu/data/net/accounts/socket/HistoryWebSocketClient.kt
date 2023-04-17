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
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import org.json.JSONObject
import ru.tsu.data.net.accounts.PayloadHistoryData
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
        val data = args[0] as JSONObject
        try {
            val message = json.decodeFromString(
                HistorySocketSerializer,
                data
                    .toString()
                    .replace("null", "\"null\"")
            )
            coroutineScope.launch {
                _messageFlow.emit(message)
            }
        } catch (e: Exception) {
            Log.e("SOCKET_EXCEPTION", e.stackTraceToString())
        }
    }

    private val socket = IO.socket(url).also { currentSocket ->
        TypeMessageHistorySocket.values().forEach {
            currentSocket.on(it.typeName, socketListener)
        }
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
            .replace("\"null\"", "null")
            .replace("\n", "")
            .trim()
        socket.emit(topic.typeName, JSONObject(message))
    }
}

object HistorySocketSerializer : JsonContentPolymorphicSerializer<HistorySocketResponse>(HistorySocketResponse::class) {
    override fun selectDeserializer(element: JsonElement) = when {
        "operations" in element.jsonObject -> HistorySocketResponse.OperationsDto.serializer()
        "type" in element.jsonObject -> HistorySocketResponse.AccountHistoryResponseDto.serializer()
        else -> HistorySocketResponse.ChangeOperationStatusDto.serializer()
    }
}

object PayloadHistorySerializer : JsonContentPolymorphicSerializer<PayloadHistoryData>(PayloadHistoryData::class) {
    override fun selectDeserializer(element: JsonElement) = when {
        "senderAccountId" in element.jsonObject -> PayloadHistoryData.Transfer.serializer()
        else -> PayloadHistoryData.WithDrawOrTopUp.serializer()
    }
}

data class SocketState(
    val isOpen: Boolean = false,
)

