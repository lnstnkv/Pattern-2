package ru.tsu.bank.details

data class AccountHistoryUiModel(
    val id: String,
    val type: String,
    val date: String,
    val ownerId: String,
    val payload: List<PayloadUiModel>,
)