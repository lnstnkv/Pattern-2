package ru.tsu.bank.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tsu.domain.authorization.model.*
import ru.tsu.domain.authorization.usecases.AuthUseCase
import ru.tsu.domain.authorization.usecases.RegistrationUseCase
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val registerUseCase: RegistrationUseCase,
) : ViewModel() {

    private val _authEvents = MutableLiveData<RegistrationToken>()
    val authEvents: LiveData<RegistrationToken> = _authEvents

    fun login(params: AuthModel) {
        authUseCase(params).onEach { result ->
            result.fold(
                onSuccess = {
                    createOwner()
                },
                onFailure = {
                    Log.e("ERROR", it.stackTraceToString())
                }
            )

        }.launchIn(viewModelScope)
    }

    private fun createOwner() {
        registerUseCase(
            RegistrationModel(
                firstName = "Muhatbek",
                lastName = "Atabek",
                password = "QWEasd123",
                username = "Galtinbek",
                role = Role.CLIENT,
                status = StatusRegister.ACTIVE,
            )
        ).onEach {
            it.fold(
                onSuccess = { token ->
                    _authEvents.postValue(token)
                },
                onFailure = {
                    // handle error
                }
            )
        }
            .launchIn(viewModelScope)
    }
}