package ru.tsu.bank.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tsu.domain.authorization.RegistrationModel
import ru.tsu.domain.authorization.RegistrationUseCase
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegistrationUseCase
) : ViewModel() {

    private val _registrationEvents = MutableLiveData<Boolean>()
    val registrationEvents: LiveData<Boolean> = _registrationEvents

    fun register(params: RegistrationModel) {
        registerUseCase(params).onEach { result ->
            _registrationEvents.postValue(result.isSuccess)
        }.launchIn(viewModelScope)
    }
}