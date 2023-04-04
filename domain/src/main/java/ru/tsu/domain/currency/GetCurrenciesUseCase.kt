package ru.tsu.domain.currency

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import javax.inject.Inject

interface GetCurrenciesUseCase : FlowUseCase<Unit, List<CurrencyModel>>
class GetCurrenciesUseCaseImpl @Inject constructor(private val dataSource: CurrencyDataSource) : GetCurrenciesUseCase {
    override fun execute(param: Unit): Flow<Result<List<CurrencyModel>>> = flow {
        // val result = dataSource.getCurrencies()
        // emit(Result.success(result))
        delay(1000)
        emit(
            Result.success(
                listOf(
                    CurrencyModel("Yuan"),
                    CurrencyModel("Dirhams"),
                    CurrencyModel("Dollar"),
                    CurrencyModel("Rubles"),
                )
            )
        )
    }
}
