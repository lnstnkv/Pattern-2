package ru.tsu.bank.details

data class AccountHistoryUiModel(
    val accountId: String,
    val type: String,
    val date: String,
    val ownerId: String,
    val amountOfMoney: Float,
)
