package ru.tsu.domain.currency

interface CurrencyDataSource {
    suspend fun getCurrencies():List<CurrencyModel>
}