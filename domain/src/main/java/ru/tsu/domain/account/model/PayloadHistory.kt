package ru.tsu.domain.account.model

data class PayloadHistory(
    val accountId:String,
    //TODO: check int in ammount
    val amountOfMoney:Int,
)