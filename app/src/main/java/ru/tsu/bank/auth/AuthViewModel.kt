package ru.tsu.bank.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tsu.domain.authorization.AuthModel
import ru.tsu.domain.authorization.AuthUseCase
import javax.inject.Inject
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _authEvents = MutableLiveData<Boolean>()
    val authEvents: LiveData<Boolean> = _authEvents

    fun login(params: AuthModel) {
        authUseCase(params).onEach { result ->
            _authEvents.postValue(result.isSuccess)
        }.launchIn(viewModelScope)
    }
}