package ru.tsu.data.net.credit

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.tsu.domain.credits.CreditParamsModel

@Serializable
data class CreditParamsDto(
    val creditDuration: Int? = null,
    val creditAmount:Int?= null,
    val tariffName:String?= null,
    val userId:Int,
) {
    fun CreditParamsDto.toDomain() = CreditParamsModel(

        creditDuration = this.creditDuration,
        creditAmount = this.creditAmount,
        tariffName = this.tariffName,
        userId=this.userId,

    )
}
internal fun CreditParamsModel.toData() = CreditParamsDto(
    creditDuration = this.creditDuration,
    creditAmount = this.creditAmount,
    tariffName = this.tariffName,
    userId= this.userId,

)