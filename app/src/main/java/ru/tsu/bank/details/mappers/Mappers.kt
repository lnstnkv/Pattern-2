package ru.tsu.bank.details.mappers

import ru.tsu.bank.details.AccountHistoryUiModel
import ru.tsu.bank.details.PayloadUiModel
import ru.tsu.domain.account.model.AccountHistoryModelInvariant
import ru.tsu.domain.account.model.PayloadHistory

fun AccountHistoryModelInvariant.toUiModel() =
    AccountHistoryUiModel(
        type = this.type,
        date = this.date,
        accountId = this.accountId,
        amountOfMoney = this.amountOfMoney,
        ownerId = this.ownerId,
    )

fun PayloadHistory.toUiModel() = PayloadUiModel(
    id = accountId,
    amountOfMoney = amountOfMoney.toString(),
)
