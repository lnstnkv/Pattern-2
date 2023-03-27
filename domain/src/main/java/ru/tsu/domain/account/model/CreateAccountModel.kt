package ru.tsu.domain.account.model

data class CreateAccountModel(
    val currency: String,
    val ownerId:String,
)