package ru.tsu.domain.account.model

data class AccountHistoryModelInvariant(
    val type: String,
    val date: String,
    val amountOfMoney: Float,
    val accountId: String,
    val ownerId: String,
)
