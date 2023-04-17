package ru.tsu.data.net.accounts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.tsu.domain.account.model.PayloadHistory

@Serializable
data class PayloadHistoryData(
    @SerialName("senderAccountId")
    val senderAccountId: String,
    @SerialName("payeeAccountId")
    val payeeAccountId: String,
    @SerialName("amountOfMoney")
    val amountOfMoney: Float,
) {
    fun toDomain() = PayloadHistory(
        senderAccountId = senderAccountId,
        payeeAccountId = payeeAccountId,
        amountOfMoney = amountOfMoney,
    )
}
