package ru.tsu.data.net.accounts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.tsu.domain.account.model.PayloadHistory

@Serializable
sealed class PayloadHistoryData {

    abstract val amountOfMoney: Float

    abstract fun toDomain(): PayloadHistory

    @Serializable
    data class Transfer(
        @SerialName("senderAccountId")
        val senderAccountId: String,
        @SerialName("payeeAccountId")
        val payeeAccountId: String,
        @SerialName("amountOfMoney")
        override val amountOfMoney: Float,
    ) : PayloadHistoryData() {
        override fun toDomain() = PayloadHistory.Transfer(
            senderAccountId = senderAccountId,
            payeeAccountId = payeeAccountId,
            amountOfMoney = amountOfMoney,
        )
    }

    @Serializable
    data class WithDrawOrTopUp(
        @SerialName("accountId")
        val accountId: String,
        @SerialName("amountOfMoney")
        override val amountOfMoney: Float,
    ) : PayloadHistoryData() {
        override fun toDomain() = PayloadHistory.WithDrawOrTopUp(
            accountId = accountId,
            amountOfMoney = amountOfMoney,
        )
    }
}


