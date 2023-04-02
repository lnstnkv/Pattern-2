package ru.tsu.domain.authorization.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import ru.tsu.domain.authorization.AuthDataSource
import ru.tsu.domain.authorization.model.AuthData
import ru.tsu.domain.authorization.model.AuthModel
import ru.tsu.domain.preferences.PreferencesDataSource

import javax.inject.Inject

interface AuthUseCase : FlowUseCase<AuthModel, AuthData>

class AuthUseCaseImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val preferencesDataSource: PreferencesDataSource,
) : AuthUseCase {
    override fun execute(param: AuthModel): Flow<Result<AuthData>> = flow {
        val result = authDataSource.login(param)
        preferencesDataSource.saveAuthData(result)
        emit(Result.success(result))
    }
}
