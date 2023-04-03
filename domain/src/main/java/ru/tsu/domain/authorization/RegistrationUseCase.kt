package ru.tsu.domain.authorization

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import javax.inject.Inject
import ru.tsu.domain.authorization.model.RegistrationModel
import ru.tsu.domain.authorization.model.RegistrationToken

interface RegistrationUseCase:FlowUseCase<RegistrationModel,RegistrationToken>

class RegistrationUseCaseImpl @Inject constructor(private val authDataSource: UserDataSource):RegistrationUseCase{
    override fun execute(param: RegistrationModel): Flow<Result<RegistrationToken>> =flow {
        val result = authDataSource.register(param)
        emit(Result.success(result))
    }
}