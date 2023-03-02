package ru.tsu.data.net.currencies

import ru.tsu.data.net.auth.AuthApi
import ru.tsu.domain.currency.CurrencyDataSource
import ru.tsu.domain.currency.CurrencyModel

class CurrencyDataSourceImpl(private val currencyApi: CurrencyApi):CurrencyDataSource {
    override suspend fun getCurrencies(): List<CurrencyModel> {
        return currencyApi.getCurrencies().map { currency -> currency.toDomain() }
    }
}