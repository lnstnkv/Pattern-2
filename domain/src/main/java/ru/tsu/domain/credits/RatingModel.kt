package ru.tsu.domain.credits

data class RatingModel(
    val id: String,
    val userId: String,
    val returnProbability: Float
)