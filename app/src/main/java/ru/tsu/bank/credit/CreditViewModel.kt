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

    private val _creditDetails = MutableLiveData<Boolean>()
    val creditDetails: LiveData<Boolean> = _creditDetails

    fun createCredit(duration: Int, amount: Int, tariff: String,ownerId:Int) {
        val currentDate = DateTimeFormatter.toServerDate(Calendar.getInstance().time)
        createCreditUseCases(
            CreditParamsModel(
                duration,
                amount,
                tariff,
                ownerId,
            )
        ).onEach { result ->
            result.fold(
                onSuccess = {
                    _creditDetails.postValue(true)
                },
                onFailure = {

                }
            )
        }.launchIn(viewModelScope)

    }

}