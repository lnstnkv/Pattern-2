package ru.tsu.bank.details

import ru.tsu.domain.account.model.AccountHistoryModel

data class AccountHistoryUiModel(
    val id: String,
    val type: String,
    val date: String,
    val ownerId: String,
    val payload: List<String>,
)