package ru.tsu.data.net.credit

import kotlinx.serialization.Serializable
import ru.tsu.domain.credits.CreditModel
import ru.tsu.domain.credits.TariffModel

@Serializable
data class TariffResponseDto(
    val id:Int? = null,
    val name:String? = null,
    val percentage:Float? = null,
    val credit: List<String>? = null,
)
internal fun TariffResponseDto.toDomain()= TariffModel(
    id = this.id,
    name = this.name,
    percentage = this.percentage,
    credit = this.credit,
)