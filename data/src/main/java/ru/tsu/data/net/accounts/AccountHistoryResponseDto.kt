package ru.tsu.data.net.accounts

import kotlinx.serialization.Serializable
import ru.tsu.domain.account.model.AccountHistoryModel

@Serializable
data class AccountHistoryResponseDto(
    val id: String,
    val type: String,
    val date: String,
    val callerId: String,
    val payload: List<PayloadHistoryData>,
) {
    fun toDomain() = AccountHistoryModel(
        id = id,
        type = type,
        date = date,
        callerId = callerId,
        payload = payload.map { it.toDomain() }
    )
}