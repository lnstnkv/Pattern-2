package ru.tsu.data.net.credit

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.tsu.domain.credits.CreditAccountModel
import ru.tsu.domain.credits.CreditModel

@Serializable
data class CreditAccountResponseDto(
    @SerialName("id")
    var id: Int? = null,
    @SerialName("creditStart")
    var creditStart: String? = null,
    @SerialName("creditDuration")
    var creditDuration: Int? = null,
    @SerialName("creditAmount")
    var creditAmount: Int? = null,
    @SerialName("tariff")
    var tariff: String? = null,
    @SerialName("debt")
    var debt: Int? = null,
    @SerialName("userId")
    var userId: String? = null,
    @SerialName("accountId")
    var accountId: String? = null,
    @SerialName("payed")
    var payed: Int? = null,
    @SerialName("closed")
    var closed: Boolean? = null
)

internal fun CreditAccountResponseDto.toDomain() = CreditAccountModel(
    id = this.id,
    creditStart = this.creditStart,
    creditDuration = this.creditDuration,
    creditAmount=this.creditAmount,
    tariff = this.tariff,
    debt = debt,
    userId = userId,
    accountId = this.accountId,
    payed = payed,
    closed=this.closed

)