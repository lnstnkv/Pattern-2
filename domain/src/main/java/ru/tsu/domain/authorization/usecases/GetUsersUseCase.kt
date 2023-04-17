package ru.tsu.domain.authorization.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import ru.tsu.domain.authorization.UserDataSource
import ru.tsu.domain.authorization.model.UserModel
import javax.inject.Inject

interface GetUsersUseCase : FlowUseCase<Unit, List<UserModel>>

class GetUsersUseCaseImpl @Inject constructor(private val dataSource: UserDataSource) :
    GetUsersUseCase {

    override fun execute(param: Unit): Flow<Result<List<UserModel>>> = flow {
        val result = dataSource.getUsers()
        emit(Result.success(result))
    }
}