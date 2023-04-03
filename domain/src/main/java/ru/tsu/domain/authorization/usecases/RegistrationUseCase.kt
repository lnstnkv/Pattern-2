package ru.tsu.domain.authorization.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import ru.tsu.domain.authorization.AuthDataSource
import ru.tsu.domain.authorization.UserDataSource
import ru.tsu.domain.authorization.model.RegistrationModel
import ru.tsu.domain.authorization.model.RegistrationToken
import javax.inject.Inject

interface RegistrationUseCase:FlowUseCase<RegistrationModel, RegistrationToken>

class RegistrationUseCaseImpl @Inject constructor(private val authDataSource: UserDataSource):
    RegistrationUseCase {
    override fun execute(param: RegistrationModel): Flow<Result<RegistrationToken>> =flow {
        val result = authDataSource.register(param)
        emit(Result.success(result))
    }
}