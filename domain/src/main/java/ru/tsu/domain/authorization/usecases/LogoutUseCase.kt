package ru.tsu.domain.authorization.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import ru.tsu.domain.preferences.PreferencesDataSource

interface LogoutUseCase : FlowUseCase<Unit, Unit>

class LogoutUseCaseImpl(private val preferencesDataSource: PreferencesDataSource) : LogoutUseCase {

    override fun execute(param: Unit): Flow<Result<Unit>> = flow {
        preferencesDataSource.clearAuthData()
        emit(Result.success(Unit))
    }
}
