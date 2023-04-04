package ru.tsu.domain.authorization.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import ru.tsu.domain.account.AccountsDataSource
import ru.tsu.domain.account.model.CreateAccountModel
import ru.tsu.domain.authorization.AuthDataSource
import ru.tsu.domain.authorization.UserDataSource
import ru.tsu.domain.authorization.model.RegistrationModel
import ru.tsu.domain.authorization.model.RegistrationToken
import javax.inject.Inject

interface RegistrationUseCase:FlowUseCase<RegistrationModel, RegistrationToken>

class RegistrationUseCaseImpl @Inject constructor(private val authDataSource: UserDataSource, private val accountsDataSource: AccountsDataSource):
    RegistrationUseCase {
    override fun execute(param: RegistrationModel): Flow<Result<RegistrationToken>> =flow {
        val result = authDataSource.register(param)
        accountsDataSource.createAccount(CreateAccountModel("Рубль",result.id.toString()))
        emit(Result.success(result))
    }
}