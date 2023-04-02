package ru.tsu.data.net.accounts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.tsu.domain.account.model.AccountsModel

@Serializable
data class AccountsResponseDto(
    @SerialName("accounts")
    val accounts: List<AccountResponseDto>,
    @SerialName("totalCount")
    val totalCount: Int,
) {
    fun toDomain() = AccountsModel(accounts.map { account -> account.toDomain() }, totalCount)
}