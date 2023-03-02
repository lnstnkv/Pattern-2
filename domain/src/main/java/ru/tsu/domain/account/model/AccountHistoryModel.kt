package ru.tsu.domain.account.model

data class AccountHistoryModel(
    val id: String,
    val type: String,
    val date: String,
    val ownerId: String,
    val payload: List<String>,
)