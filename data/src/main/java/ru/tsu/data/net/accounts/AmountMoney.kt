package ru.tsu.data.net.accounts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AmountMoney(
    @SerialName("amountOfMoney")
    private val value:Float,
)