package ru.tsu.domain.authorization

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import ru.tsu.domain.authorization.model.AuthToken
import ru.tsu.domain.authorization.model.AuthModel

import javax.inject.Inject

interface AuthUseCase: FlowUseCase<AuthModel, AuthToken>
class AuthUseCaseImp @Inject constructor(private val authDataSource: AuthDataSource):AuthUseCase {
    override fun execute(param: AuthModel): Flow<Result<AuthToken>> = flow{
        val result = authDataSource.login(param)
        emit(Result.success(result))
    }
}