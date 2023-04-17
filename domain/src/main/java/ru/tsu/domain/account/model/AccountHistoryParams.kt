package ru.tsu.domain.account.model

data class AccountHistoryParams(
    val id: String,
    val pagination: PaginationParams,
)

data class PaginationParams(
    val limit: String?,
    val skip: String?,
)
