package ru.tsu.domain.credits

data class PaymentModel(
    val id: String,
    val timestamp: String,
    val accountId: String,
    val payed: Float,
)