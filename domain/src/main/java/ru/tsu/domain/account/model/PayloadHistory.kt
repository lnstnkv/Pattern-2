package ru.tsu.domain.account.model

sealed class PayloadHistory {
    abstract val amountOfMoney: Float

    data class Transfer(
        val senderAccountId: String,
        val payeeAccountId: String,
        override val amountOfMoney: Float,
    ) : PayloadHistory()

    data class WithDrawOrTopUp(
        val accountId: String,
        override val amountOfMoney: Float,
    ) : PayloadHistory()
}
