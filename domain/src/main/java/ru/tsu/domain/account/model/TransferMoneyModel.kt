package ru.tsu.domain.account.model

data class TransferMoneyModel(
    val accountId: String,
    val receiverId:String,
    val amountOfMoney: Float,
)