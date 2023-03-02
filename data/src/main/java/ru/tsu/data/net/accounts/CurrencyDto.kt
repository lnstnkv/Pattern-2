package ru.tsu.data.net.accounts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.tsu.domain.account.model.CurrencyModel

@Serializable
data class CurrencyDto(
    @SerialName("currency")
    val currency: String
)

internal fun CurrencyModel.toData() = CurrencyDto(
    currency = this.currency
)