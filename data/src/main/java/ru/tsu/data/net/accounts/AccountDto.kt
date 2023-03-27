package ru.tsu.data.net.accounts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.tsu.domain.account.model.CreateAccountModel

@Serializable
data class AccountDto(
    @SerialName("currency")
    val currency: String,
    @SerialName("ownerId")
    val ownerId: String
)

internal fun CreateAccountModel.toData() = AccountDto(
    currency = this.currency,
    ownerId = this.ownerId,
)