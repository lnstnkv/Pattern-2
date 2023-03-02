package ru.tsu.domain.account.model

data class AccountModel(
    val id:String,
    val balance:Float,
    val ownerId:String,
    val currency: Currency,
    val isDeleted:Boolean,
    val isBlocked:Boolean,
)