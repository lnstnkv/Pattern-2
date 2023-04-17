package ru.tsu.data.net.accounts.socket

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.tsu.domain.account.model.AccountHistoryParams
import ru.tsu.domain.account.model.PaginationParams

@Serializable
data class AccountHistoryParamsDto(
    @SerialName("id")
    val id: String,
    @SerialName("pagination")
    val pagination: PaginationParamsDto,
) {
    companion object {
        fun fromDomain(params: AccountHistoryParams) = AccountHistoryParamsDto(
            id = params.id,
            pagination = PaginationParamsDto.fromDomain(params.pagination)
        )
    }
}

@Serializable
data class PaginationParamsDto(
    @SerialName("limit")
    val limit: String?,
    @SerialName("skip")
    val skip: String?,
) {
    companion object {
        fun fromDomain(params: PaginationParams) = PaginationParamsDto(
            limit = params.limit,
            skip = params.skip
        )
    }
}
