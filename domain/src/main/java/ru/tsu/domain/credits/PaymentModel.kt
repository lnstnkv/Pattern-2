package ru.tsu.domain.credits

data class PaymentModel(
    val id: Int,
    val timestamp: Long,
    val accountId: String,
    val payed: Float,
)
