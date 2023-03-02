package ru.tsu.data.net.credit

import ru.tsu.domain.credits.CreditModel

@kotlinx.serialization.Serializable
data class CreditResponseDto(
    val id: Int? = null,
    val creditStart: String? = null,
    val creditDuration: Int? = null,
    val tariff: TariffResponseDto? = null,
)

internal fun CreditResponseDto.toDomain() = CreditModel(
    id = this.id,
    creditStart = this.creditStart,
    creditDuration = this.creditDuration,
    tariff = this.tariff?.toDomain(),
)