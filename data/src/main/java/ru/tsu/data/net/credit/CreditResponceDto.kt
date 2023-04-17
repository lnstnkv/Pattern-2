package ru.tsu.data.net.credit

import kotlinx.serialization.Serializable
import ru.tsu.domain.credits.CreditModel

@Serializable
data class CreditResponseDto(
    val id: Int? = null,
    val creditStart: String? = null,
    val creditDuration: Int? = null,
    val tariff: TariffResponseDto? = null,
    val debt: Float? = null,
    val userId: Int? = null,
    val payed: Float? = null,

    )

internal fun CreditResponseDto.toDomain() = CreditModel(
    id = this.id,
    creditStart = this.creditStart,
    creditDuration = this.creditDuration,
    tariff = this.tariff?.toDomain(),
    debt = debt, userId = userId, payed = payed

)