package ru.tsu.data.net.currencies

import kotlinx.serialization.Serializable
import ru.tsu.domain.currency.CurrencyModel

@Serializable
data class CurrencyResponseDto(
    val name:String
){
    fun toDomain()= CurrencyModel(name)
}