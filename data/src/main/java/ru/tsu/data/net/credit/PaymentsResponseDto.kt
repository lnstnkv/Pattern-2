package ru.tsu.data.net.credit

import kotlinx.serialization.Serializable
import ru.tsu.domain.credits.PaymentModel

@Serializable
data class PaymentsResponseDto(
    val id: Int,
    val timestamp: Long,
    val accountId: String,
    val payed: Float,
)
internal fun PaymentsResponseDto.toDomain() = PaymentModel(
    id = this.id,
    timestamp = this.timestamp,
    accountId = this.accountId,
    payed = payed,

)
