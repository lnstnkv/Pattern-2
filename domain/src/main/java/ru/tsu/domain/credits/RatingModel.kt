package ru.tsu.domain.credits

data class RatingModel(
    val id: Int,
    val userId: String,
    val returnProbability: Float
)