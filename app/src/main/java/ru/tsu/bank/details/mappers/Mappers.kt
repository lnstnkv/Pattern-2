package ru.tsu.bank.details.mappers

import ru.tsu.bank.details.AccountHistoryUiModel
import ru.tsu.domain.account.model.AccountHistoryModel

fun AccountHistoryModel.toUiModel() =
    AccountHistoryUiModel(
        id = this.id,
        type = this.type,
        date = this.date,
        ownerId = this.ownerId,
        payload = this.payload
    )