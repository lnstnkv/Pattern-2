package ru.tsu.data.net.accounts

import kotlinx.serialization.Serializable
import ru.tsu.domain.account.model.AccountHistoryModel

@Serializable
data class AccountHistoryResponseDto(
    val id: String,
    val type: String,
    val date: String,
    val ownerId: String,
    val payload: List<String>,
) {
    fun toDomain() = AccountHistoryModel(
        id = id,
        type = type,
        date = date,
        ownerId = ownerId,
        payload = payload
    )
}