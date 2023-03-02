package ru.tsu.domain.currency

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import ru.tsu.domain.account.AccountsDataSource
import javax.inject.Inject

interface GetCurrenciesUseCase: FlowUseCase<Unit, List<CurrencyModel>>
class GetCurrenciesUseCaseImpl @Inject constructor(private val dataSource: CurrencyDataSource):GetCurrenciesUseCase {
    override fun execute(param: Unit): Flow<Result<List<CurrencyModel>>> = flow{
        val result = dataSource.getCurrencies()
        emit(Result.success(result))
    }
}