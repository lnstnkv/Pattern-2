package ru.tsu.domain.authorization

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import ru.tsu.domain.account.AccountsDataSource
import ru.tsu.domain.account.model.CreateAccountModel
import javax.inject.Inject
import ru.tsu.domain.authorization.model.RegistrationModel
import ru.tsu.domain.authorization.model.RegistrationToken

interface RegistrationUseCase:FlowUseCase<RegistrationModel,RegistrationToken>

class RegistrationUseCaseImpl @Inject constructor(private val authDataSource: UserDataSource,private val accountDataSource: AccountsDataSource):RegistrationUseCase{
    override fun execute(param: RegistrationModel): Flow<Result<RegistrationToken>> =flow {
        val result = authDataSource.register(param)
        accountDataSource.createAccount(CreateAccountModel("Рубль",result.id.toString()))
        emit(Result.success(result))

    }
}