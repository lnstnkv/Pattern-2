package ru.tsu.domain.credits

data class CreditAccountModel(
    var id: Int,
    var creditStart: Long? = null,
    var creditDuration: Int? = null,
    var creditAmount: Float,
    var tariff: String? = null,
    var debt: Float? = null,
    var userId: String? = null,
    var accountId: String? = null,
    var payed: Float,
    var closed: Boolean? = null
)