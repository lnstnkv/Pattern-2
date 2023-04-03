package ru.tsu.data.net.accounts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.tsu.domain.account.model.PayloadHistory

@Serializable
data class PayloadHistoryData(
    @SerialName("accountId")
    val accountId: String,
//TODO: check int in ammount
    @SerialName("amountOfMoney")
    val amountOfMoney: Int,
) {
    fun toDomain() = PayloadHistory(
        accountId = accountId,
        amountOfMoney = amountOfMoney,

        )
}