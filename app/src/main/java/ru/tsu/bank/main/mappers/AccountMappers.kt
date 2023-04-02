package ru.tsu.bank.main.mappers

import ru.tsu.bank.main.AccountUiModel
import ru.tsu.domain.account.model.AccountModel

fun AccountModel.toUiModel() = AccountUiModel(
    id = this.id,
    number=this.id,
    value = this.balance,
   // currency = this.currency.name
)