package ru.tsu.bank.details.mappers

import ru.tsu.bank.details.AccountHistoryUiModel
import ru.tsu.domain.account.model.AccountHistoryModelInvariant
import ru.tsu.domain.account.model.HistoryEvent
import ru.tsu.domain.account.model.PayloadHistory

fun AccountHistoryModelInvariant.toUiModel() =
    AccountHistoryUiModel(
        type = this.type,
        date = this.date,
        accountId = this.accountId,
        amountOfMoney = this.amountOfMoney,
        ownerId = this.ownerId,
    )

fun HistoryEvent.HistoryModel.toUiModel() =
    AccountHistoryUiModel(
        type = this.type,
        date = this.date,
        accountId = when (val payload = this.payload) {
            is PayloadHistory.Transfer -> payload.payeeAccountId
            is PayloadHistory.WithDrawOrTopUp -> payload.accountId
        },
        amountOfMoney = this.payload.amountOfMoney,
        ownerId = this.callerId,
    )
