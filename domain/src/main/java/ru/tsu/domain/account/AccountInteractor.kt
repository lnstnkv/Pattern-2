package ru.tsu.domain.account

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.tsu.domain.account.model.AccountHistoryParams
import ru.tsu.domain.account.model.PaginationParams

class AccountInteractor(
    private val dataSource: AccountHistoryDataSource
) {

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        Log.d("ACCOUNT_INTERACTOR_EXCEPTION", e.stackTraceToString())
    }
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO + exceptionHandler)

    private val _state = MutableStateFlow(AccountSourceState())

    init {
        coroutineScope.launch {
            dataSource.sourceIsActive
                .onEach { isActive ->
                    _state.update { it.copy(isActive = isActive) }
                }
                .launchIn(coroutineScope)
        }
    }

    val sourceIsActive = _state.map { it.isActive }

    val messageFlow
        get() = dataSource.messageFlow

    fun getHistory(id: String, limit: Int? = null, skip: Int? = null) {
        dataSource.send(
            AccountHistoryParams(
                id = id,
                pagination = PaginationParams(
                    limit = limit.toString(),
                    skip = skip.toString(),
                )
            )
        )
    }
}

data class AccountSourceState(
    val isActive: Boolean = false,
)
