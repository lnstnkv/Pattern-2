package ru.tsu.domain.credits

data class CreditParamsModel(
    val creditStart: String? = null,
    val creditDuration: Int? = null,
    val creditAmount:Int?= null,
    val tariffName:String?= null,
)