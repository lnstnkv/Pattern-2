package ru.tsu.bank.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tsu.bank.main.mappers.toUiModel
import ru.tsu.domain.account.model.OwnerId
import ru.tsu.domain.account.usecases.GetListAccountUseCase
import ru.tsu.domain.credits.GetCreditAccountUseCase
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val useCase: GetListAccountUseCase,
    private val getCreditAccountUseCase: GetCreditAccountUseCase,
) :
    ViewModel() {
    private val _accountsEvents = MutableLiveData<List<AccountUiModel>>()
    val accountsEvents: LiveData<List<AccountUiModel>> = _accountsEvents

    private val _accountCreditEvents = MutableLiveData<List<AccountUiModel>>()
    val accountCreditEvents: LiveData<List<AccountUiModel>> = _accountCreditEvents

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow: Flow<String> = _errorFlow

    fun getListAccounts(id: String) {
        useCase(OwnerId(id)).onEach { result ->
            result.fold(
                onSuccess = { accounts ->
                    _accountsEvents.postValue(accounts.accounts.map { it.toUiModel() })
                },
                onFailure = {
                    _errorFlow.emit("Не удалось отобразить список ваших счетов, попробуйте позже")
                }
            )
        }.launchIn(viewModelScope)
    }

    fun getListCreditAccount(id:String) {
        getCreditAccountUseCase(id).onEach{ result ->
            result.fold(
                onSuccess = { accounts ->
                    _accountCreditEvents.postValue(accounts.map { it.toUiModel() })
                },
                onFailure = {
                    _errorFlow.emit("Не удалось отобразить список ваших счетов, попробуйте позже")
                }
            )
        }.launchIn(viewModelScope)
    }

}
