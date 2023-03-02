package ru.tsu.domain.authorization.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import ru.tsu.domain.authorization.AuthDataSource
import ru.tsu.domain.authorization.model.AuthModel
import ru.tsu.domain.authorization.model.AuthToken
import javax.inject.Inject

interface AuthUseCase: FlowUseCase<AuthModel, AuthToken>
class AuthUseCaseImpl @Inject constructor(private val authDataSource: AuthDataSource): AuthUseCase {
    override fun execute(param: AuthModel): Flow<Result<AuthToken>> = flow{
        val result = authDataSource.login(param)
        emit(Result.success(result))
    }
}