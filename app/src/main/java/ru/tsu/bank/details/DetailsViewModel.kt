package ru.tsu.bank.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.tsu.bank.details.mappers.toUiModel
import ru.tsu.bank.main.AccountUiModel
import ru.tsu.bank.main.mappers.toUiModel
import ru.tsu.domain.account.AccountInteractor
import ru.tsu.domain.account.model.AccountId
import ru.tsu.domain.account.model.HistoryEvent
import ru.tsu.domain.account.model.OperationStatus
import ru.tsu.domain.account.usecases.DeleteAccountUseCase
import ru.tsu.domain.account.usecases.GetAccountHistoryUseCase
import ru.tsu.domain.account.usecases.GetAccountUseCase
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val getAccountHistoryUseCase: GetAccountHistoryUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val accountInteractor: AccountInteractor,
) : ViewModel() {

    private val _accountsEvents = MutableLiveData<DetailsEvents>()
    val accountsEvents: LiveData<DetailsEvents> = _accountsEvents

    private val _accountsHistoryEvents = MutableLiveData<List<AccountHistoryUiModel>>()
    val accountsHistoryEvents: LiveData<List<AccountHistoryUiModel>> = _accountsHistoryEvents

    private val _accountDetails = MutableLiveData<AccountUiModel>()
    val accountDetails: LiveData<AccountUiModel> = _accountDetails

    private val _rejectedEvents = MutableSharedFlow<Unit>()
    val rejectedEvents: Flow<Unit> = _rejectedEvents

    private var historyJob: Job? = null
    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow: Flow<String> = _errorFlow

    private lateinit var accountId: String

    fun fetchHistoryData(accountId: String) {
        this.accountId = accountId
        historyJob?.cancel()
        historyJob = accountInteractor.messageFlow
            .onEach(::handleHistoryEvent)
            .launchIn(viewModelScope)
        accountInteractor.getHistory(accountId)
    }

    fun deleteAccount(accountId: String) {
        deleteAccountUseCase(AccountId(accountId)).onEach { result ->
            result.fold(onSuccess = {
                _accountsEvents.postValue(DetailsEvents.AccountWasBlocked)
            }, onFailure = {
                _errorFlow.emit("Не удалось заблокировать счет,попробуйте позже")
            })
        }.launchIn(viewModelScope)
    }

    fun getAccount(accountId: String) {
        getAccountUseCase(AccountId(accountId)).onEach { result ->
            result.fold(onSuccess = { account ->
                _accountDetails.postValue(account.toUiModel())
            }, onFailure = {
                _errorFlow.emit("Не удалось получить данные по счету")
            })
        }.launchIn(viewModelScope)
    }

    private fun handleHistoryEvent(event: HistoryEvent) {
        when (event) {
            is HistoryEvent.ChangeOperationStatusModel -> {
                when (event.status) {
                    OperationStatus.Pending -> Unit
                    OperationStatus.Rejected -> viewModelScope.launch { _rejectedEvents.emit(Unit) }
                    OperationStatus.Accepted -> Unit
                }
                getAccount(accountId)
            }

            is HistoryEvent.HistoryModel -> {
                val currentList = _accountsHistoryEvents.value ?: return
                if (currentList.contains(event.toUiModel())) return
                val newData = currentList + event.toUiModel()
                _accountsHistoryEvents.postValue(newData)
            }

            is HistoryEvent.OperationsModel -> {
                _accountsHistoryEvents.postValue(event.operations.map { it.toUiModel() })
            }
        }
    }

}

sealed class DetailsEvents {
    object AccountWasBlocked : DetailsEvents()
}

