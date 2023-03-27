package ru.tsu.domain.credits

data class CreditParamsModel(
    val creditDuration: Int? = null,
    val creditAmount:Int?= null,
    val tariffName:String?= null,
    val userId:Int,
)