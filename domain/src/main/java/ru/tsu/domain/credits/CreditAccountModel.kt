package ru.tsu.domain.credits

data class CreditAccountModel(
    var id: Int? = null,
    var creditStart: String? = null,
    var creditDuration: Int? = null,
    var creditAmount: Int? = null,
    var tariff: String? = null,
    var debt: Int? = null,
    var userId: String? = null,
    var accountId: String? = null,
    var payed: Int? = null,
    var closed: Boolean? = null
)