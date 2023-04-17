package ru.tsu.bank.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tsu.bank.details.mappers.toUiModel
import ru.tsu.bank.main.AccountUiModel
import ru.tsu.bank.main.mappers.toUiModel
import ru.tsu.domain.account.model.AccountId
import kotlinx.coroutines.flow.Flow
import ru.tsu.domain.account.usecases.*
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val getAccountHistoryUseCase: GetAccountHistoryUseCase,
    private val getAccountUseCase: GetAccountUseCase,
) : ViewModel() {

    private val _accountsEvents = MutableLiveData<DetailsEvents>()
    val accountsEvents: LiveData<DetailsEvents> = _accountsEvents

    private val _accountsHistoryEvents = MutableLiveData<List<AccountHistoryUiModel>>()
    val accountsHistoryEvents: LiveData<List<AccountHistoryUiModel>> = _accountsHistoryEvents

    private val _accountDetails = MutableLiveData<AccountUiModel>()
    val accountDetails: LiveData<AccountUiModel> = _accountDetails

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow: Flow<String> = _errorFlow


    fun getAccountHistory(accountId: String) {
        getAccountHistoryUseCase(AccountId(accountId)).onEach { result ->
            result.fold(onSuccess = { list ->
                _accountsHistoryEvents.postValue(list.map { element -> element.toUiModel() })
            }, onFailure = {

            })
        }.launchIn(viewModelScope)
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

}

sealed class DetailsEvents {
    object AccountWasBlocked : DetailsEvents()
}