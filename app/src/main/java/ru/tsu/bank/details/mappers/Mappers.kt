package ru.tsu.bank.details.mappers

import ru.tsu.bank.details.AccountHistoryUiModel
import ru.tsu.bank.details.PayloadUiModel
import ru.tsu.domain.account.model.AccountHistoryModel
import ru.tsu.domain.account.model.PayloadHistory

fun AccountHistoryModel.toUiModel() =
    AccountHistoryUiModel(
        id = this.id,
        type = this.type,
        date = this.date,
        ownerId = this.callerId,
        payload = this.payload.map { it->it.toUiModel() }
    )

fun PayloadHistory.toUiModel()=PayloadUiModel(
    id = accountId,
    amountOfMoney = amountOfMoney.toString(),
)