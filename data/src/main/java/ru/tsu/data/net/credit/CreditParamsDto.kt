package ru.tsu.data.net.credit

import kotlinx.serialization.Serializable
import ru.tsu.data.net.accounts.CurrencyDto
import ru.tsu.data.net.auth.RegisterParamsDto
import ru.tsu.domain.account.model.CurrencyModel
import ru.tsu.domain.authorization.model.RegistrationModel
import ru.tsu.domain.credits.CreditParamsModel

@Serializable
data class CreditParamsDto(
    val creditStart: String? = null,
    val creditDuration: Int? = null,
    val creditAmount:Int?= null,
    val tariffName:String?= null,
) {
    fun CreditParamsDto.toDomain() = CreditParamsModel(

        creditStart = this.creditStart,
        creditDuration = this.creditDuration,
        creditAmount = this.creditAmount,
        tariffName = this.tariffName,
    )
}
internal fun CreditParamsModel.toData() = CreditParamsDto(
    creditStart = this.creditStart,
    creditDuration = this.creditDuration,
    creditAmount = this.creditAmount,
    tariffName = this.tariffName,
)