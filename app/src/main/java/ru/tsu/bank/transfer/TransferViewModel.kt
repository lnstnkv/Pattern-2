package ru.tsu.bank.transfer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tsu.domain.account.model.TransferMoneyModel
import ru.tsu.domain.operations.usecases.TransferMoneyUseCase
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    private val transferMoneyUseCase: TransferMoneyUseCase,
) : ViewModel() {

    private val _transferEvents = MutableLiveData<Unit>()
    val transferEvents: LiveData<Unit> = _transferEvents

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow: Flow<String> = _errorFlow

    fun transfer(accountId: String, fromAccountId: String, amount: Float) {
        transferMoneyUseCase(
            TransferMoneyModel(
                accountId,
                fromAccountId,
                amount
            )
        ).onEach { result ->
            result.fold(
                onSuccess = {
                    _transferEvents.postValue(Unit)
                },
                onFailure = {
                    _errorFlow.emit("Не удалось перевести")
                }
            )
        }
            .launchIn(viewModelScope)
    }
}