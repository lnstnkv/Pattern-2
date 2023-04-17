package ru.tsu.domain.account.model

data class PayloadHistory(
    val senderAccountId: String,
    val payeeAccountId: String,
    val amountOfMoney: Float,
)
