package ru.tsu.data.net.credit

import kotlinx.serialization.Serializable
import ru.tsu.domain.credits.CreditAccountModel
import ru.tsu.domain.credits.PaymentModel

@Serializable
data class PaymentsResponseDto(
    val id: String,
    val timestamp: String,
    val accountId: String,
    val payed: Float,
)
internal fun PaymentsResponseDto.toDomain() = PaymentModel(
    id = this.id,
    timestamp = this.timestamp,
    accountId = this.accountId,
    payed = payed,

)