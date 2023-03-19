package ru.tsu.bank.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tsu.bank.register.mappers.toUiModel
import ru.tsu.domain.authorization.model.RegistrationModel
import ru.tsu.domain.authorization.usecases.RegistrationUseCase
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegistrationUseCase
) : ViewModel() {

    private val _registrationEvents = MutableLiveData<RegisterUiModel>()
    val registrationEvents: LiveData<RegisterUiModel> = _registrationEvents

    fun register(params: RegistrationModel) {
        registerUseCase(params).onEach { result ->
            result.fold(
                onSuccess = { result ->
                    Log.e("Model",result.toString())
                    _registrationEvents.postValue(result.toUiModel())
                },
                onFailure = {
                    Log.e("Model",it.stackTraceToString())
                }
            )
        }.launchIn(viewModelScope)
    }
}