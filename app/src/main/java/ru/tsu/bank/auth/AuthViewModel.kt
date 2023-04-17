package ru.tsu.bank.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tsu.domain.authorization.model.*
import ru.tsu.domain.authorization.usecases.AuthUseCase
import ru.tsu.domain.authorization.usecases.GetUsersUseCase
import ru.tsu.domain.authorization.usecases.RegistrationUseCase
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val registerUseCase: RegistrationUseCase,
    private val getUsersUseCase: GetUsersUseCase,
) : ViewModel() {

    private val _authEvents = MutableLiveData<UserModel>()
    val authEvents: LiveData<UserModel> = _authEvents

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow: Flow<String> = _errorFlow

    fun login(params: AuthModel) {
        authUseCase(params).onEach { result ->
            result.fold(
                onSuccess = {
                    createOwnerIfNeeded()
                },
                onFailure = {
                    _errorFlow.emit("Произошла ошибка при авторизации")
                }
            )

        }.launchIn(viewModelScope)
    }

    private fun createOwnerIfNeeded() {
        getUsersUseCase(Unit)
            .onEach {
                it.fold(
                    onSuccess = { userList ->
                        val user = userList.find { item -> item.username == PERMANENT_USER }
                        if(user == null) createPermanentOwner()
                        else _authEvents.postValue(user)
                    },
                    onFailure = {
                        _errorFlow.emit("Произошла ошибка при получении списка пользователей")
                    }
                )
            }
            .launchIn(viewModelScope)
    }

    private fun createPermanentOwner() {
        registerUseCase(
            RegistrationModel(
                firstName = "Muhatbek",
                lastName = "Atabek",
                password = "QWEasd123",
                username = PERMANENT_USER,
                role = Role.CLIENT,
                status = StatusRegister.ACTIVE,
            )
        ).onEach {
            it.fold(
                onSuccess = { token ->
                    _authEvents.postValue(token)
                },
                onFailure = {
                    _errorFlow.emit("Произошла ошибка при создании пользователя")
                }
            )
        }.launchIn(viewModelScope)
    }

    companion object {
        private const val PERMANENT_USER = "Galtinbe@mail.ru"
    }
}