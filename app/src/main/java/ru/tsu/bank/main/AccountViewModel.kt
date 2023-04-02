package ru.tsu.bank.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tsu.bank.main.mappers.toUiModel
import ru.tsu.domain.account.model.OwnerId
import ru.tsu.domain.account.usecases.GetListAccountUseCase
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val useCase: GetListAccountUseCase
) :
    ViewModel() {
    private val _accountsEvents = MutableLiveData<List<AccountUiModel>>()
    val accountsEvents: LiveData<List<AccountUiModel>> = _accountsEvents

    fun getListAccounts(id: String) {
        useCase(OwnerId(id)).onEach { result ->
            result.fold(
                onSuccess = { accounts ->
                    _accountsEvents.postValue(accounts.accounts.map { it.toUiModel() })
                },
                onFailure = {
                    Log.e("error",it.stackTraceToString())
                }
            )
        }.launchIn(viewModelScope)
    }
}
