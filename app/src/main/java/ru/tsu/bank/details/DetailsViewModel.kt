package ru.tsu.bank.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tsu.bank.details.mappers.toUiModel
import ru.tsu.bank.main.AccountUiModel
import ru.tsu.bank.main.mappers.toUiModel
import ru.tsu.domain.account.AccountInteractor
import ru.tsu.domain.account.model.AccountId
import kotlinx.coroutines.flow.Flow
import ru.tsu.domain.account.usecases.*
import ru.tsu.domain.account.model.HistoryEvent
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

    private var historyJob: Job? = null
    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow: Flow<String> = _errorFlow


    fun fetchHistoryData(accountId: String) {
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
            is HistoryEvent.ChangeOperationStatusModel -> Unit // TODO: handle
            is HistoryEvent.HistoryModel -> {
                val currentList = _accountsHistoryEvents.value ?: return
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

