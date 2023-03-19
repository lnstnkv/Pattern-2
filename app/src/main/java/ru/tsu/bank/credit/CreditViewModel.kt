package ru.tsu.bank.credit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tsu.domain.credits.CreateCreditUseCases
import ru.tsu.domain.credits.CreditParamsModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreditViewModel @Inject constructor(
    private val createCreditUseCases: CreateCreditUseCases,
) : ViewModel() {

    private val _creditDetails = MutableLiveData<Unit>()
    val creditDetails: LiveData<Unit> = _creditDetails

    fun createCredit(duration: Int, amount: Int, tariff: String) {
        val currentDate = DateTimeFormatter.toServerDate(Calendar.getInstance().time)
        createCreditUseCases(
            CreditParamsModel(
                currentDate,
                duration,
                amount,
                tariff
            )
        ).onEach { result ->
            result.fold(
                onSuccess = {
                    _creditDetails.postValue(Unit)
                },
                onFailure = {

                }
            )
        }.launchIn(viewModelScope)

    }

}