package ru.tsu.domain.credits

data class TariffModel(
    val id: Int? = null,
    val name: String? = null,
    val percentage: Float? = null,
    val credit: List<String>? = null,
)