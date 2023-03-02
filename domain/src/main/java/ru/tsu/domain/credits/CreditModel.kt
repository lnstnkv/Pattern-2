package ru.tsu.domain.credits

data class CreditModel(
    val id: Int? = null,
    val creditStart: String? = null,
    val creditDuration: Int? = null,
    val tariff: TariffModel? = null,
)