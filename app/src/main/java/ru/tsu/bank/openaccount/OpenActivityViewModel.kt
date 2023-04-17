package ru.tsu.bank.openaccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tsu.bank.main.AccountUiModel
import ru.tsu.bank.main.mappers.toUiModel
import ru.tsu.domain.account.model.CreateAccountModel
import ru.tsu.domain.account.usecases.CreateAccountUseCase
import ru.tsu.domain.currency.GetCurrenciesUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class OpenActivityViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val createAccountUseCase: CreateAccountUseCase
) :
    ViewModel() {

    private val _currenciesList = MutableLiveData<List<String>>()
    val currenciesList: LiveData<List<String>> = _currenciesList

    private val _accountModel = MutableLiveData<AccountUiModel>()
    val accountModel: LiveData<AccountUiModel> = _accountModel

    private val _selectedCurrency = MutableLiveData<String>()
    val selectedCurrency: LiveData<String> = _selectedCurrency

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow: Flow<String> = _errorFlow

    fun getCurrencies() {
        getCurrenciesUseCase(Unit).onEach { result ->
            result.fold(
                onSuccess = { currencyList ->
                    _currenciesList.postValue(currencyList.map { currency -> currency.value })
                },
                onFailure = {

                }
            )

        }.launchIn(viewModelScope)
    }

    fun selectCurrency(currency: String) {
        _selectedCurrency.value = currency
    }

    fun createAccount(ownerId:String,currency: String) {
        createAccountUseCase(CreateAccountModel("currency",ownerId)).onEach { result ->
            result.fold(
                onSuccess = { account ->
                    _accountModel.postValue(account.toUiModel())
                },
                onFailure = {
                _errorFlow.emit("Не удалось открыть счет")
                }
            )
        }.launchIn(viewModelScope)
    }
}