package ru.tsu.domain.account.model

data class AccountHistoryModel(
    val id: String,
    val type: String,
    val date: String,
    val callerId: String,
    val payload: List<PayloadHistory>,
)