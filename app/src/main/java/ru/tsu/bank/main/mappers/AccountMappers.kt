package ru.tsu.bank.main.mappers

import ru.tsu.bank.main.AccountUiModel
import ru.tsu.domain.account.model.AccountModel
import ru.tsu.domain.credits.CreditAccountModel
import ru.tsu.domain.credits.PaymentModel

fun AccountModel.toUiModel() = AccountUiModel(
    id = this.id,
    number=this.id,
    value = this.balance,
   // currency = this.currency.name
)

fun CreditAccountModel.toUiModel() = AccountUiModel(
    id = this.id.toString(),
    number=this.creditDuration.toString(),
    value = this.creditAmount,
)
fun PaymentModel.toUiModel() = AccountUiModel(
    id = this.id,
    number=this.accountId,
    value = this.payed,
)