package ru.tsu.bank.amount

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tsu.domain.account.model.MoneyAmountModel
import ru.tsu.domain.account.usecases.TopUpAccountUseCase
import ru.tsu.domain.account.usecases.WithdrawMoneyFromAccountUseCase
import javax.inject.Inject

@HiltViewModel
class AmountViewModel @Inject constructor(
    private val withdrawMoneyFromAccountUseCase: WithdrawMoneyFromAccountUseCase,
    private val topUpAccountUseCase: TopUpAccountUseCase,
) : ViewModel() {

    private val _withdrawAccounts = MutableLiveData<Unit>()
    val withdrawAccounts: LiveData<Unit> = _withdrawAccounts

    private val _topUpAccounts = MutableLiveData<Unit>()
    val topUpAccounts: LiveData<Unit> = _topUpAccounts

    fun withdraw(accountId: String, amount: String) {
        withdrawMoneyFromAccountUseCase(
            MoneyAmountModel(
                accountId,
                amount.toFloat()
            )
        ).onEach { result ->
            result.fold(
                onSuccess = {
                    _withdrawAccounts.postValue(Unit)
                },
                onFailure = {
                    Log.e("error2", it.stackTraceToString())
                }
            )

        }.launchIn(viewModelScope)
    }

    fun topUp(accountId: String, amount: String) {
        topUpAccountUseCase(
            MoneyAmountModel(
                accountId,
                amount.toFloat()
            )
        ).onEach { result ->
            result.fold(
                onSuccess = {
                    _topUpAccounts.postValue(Unit)
                },
                onFailure = {
                    Log.e("error", it.stackTraceToString())
                }
            )

        }.launchIn(viewModelScope)
    }
}