package ru.tsu.bank.credit_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tsu.bank.main.AccountUiModel
import ru.tsu.bank.main.mappers.toUiModel
import ru.tsu.domain.account.model.OwnerId
import ru.tsu.domain.credits.GetListOverduePaymentUseCase
import ru.tsu.domain.credits.GetListPaymentUseCase
import ru.tsu.domain.credits.GetRatingUseCase
import javax.inject.Inject

@HiltViewModel
class CreditDetailsViewModel @Inject constructor(
    private val getListOverduePaymentUseCase: GetListOverduePaymentUseCase,
    private val getListPaymentUseCase: GetListPaymentUseCase,
    private val getRatingUseCase: GetRatingUseCase,
) : ViewModel()  {

    private val _ratingEvents = MutableLiveData<String>()
    val ratingEvents: LiveData<String> = _ratingEvents

    private val _paymentEvents = MutableLiveData<List<AccountUiModel>>()
    val paymentEvents: LiveData<List<AccountUiModel>> = _paymentEvents

    private val _overduePaymentCreditEvents = MutableLiveData<List<AccountUiModel>>()
    val overduePaymentCreditEvents: LiveData<List<AccountUiModel>> = _overduePaymentCreditEvents

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow: Flow<String> = _errorFlow

    fun rating(userId:String){
        getRatingUseCase(userId).onEach {result->
            result.fold(
                onSuccess = {
                    _ratingEvents.postValue(it.returnProbability.toString())
                },
                onFailure = {
                    _errorFlow.emit("Не удалось посчитать ваш балл, попробуйте позже")
                }
            )
        }.launchIn(viewModelScope)
    }

    fun getListPayment(id: String) {
        getListPaymentUseCase(id).onEach { result ->
            result.fold(
                onSuccess = { accounts ->
                    _paymentEvents.postValue(accounts.map { it.toUiModel() })
                },
                onFailure = {
                    _errorFlow.emit("Не удалось отобразить список ваших оплат, попробуйте позже")
                }
            )
        }.launchIn(viewModelScope)
    }

    fun getListOverduePayment(id: String) {
        getListPaymentUseCase(id).onEach { result ->
            result.fold(
                onSuccess = { accounts ->
                    _overduePaymentCreditEvents.postValue(accounts.map { it.toUiModel() })
                },
                onFailure = {
                    _errorFlow.emit("Не удалось отобразить список ваших просроченных оплат, попробуйте позже")
                }
            )
        }.launchIn(viewModelScope)
    }



}