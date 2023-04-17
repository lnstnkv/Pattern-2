package ru.tsu.data.net.credit

import kotlinx.serialization.Serializable
import ru.tsu.domain.credits.CreditModel
import ru.tsu.domain.credits.RatingModel

@Serializable
data class RatingResponse(
    val id: Int,
    val userId: String,
    val returnProbability: Float
)

internal fun RatingResponse.toDomain() = RatingModel(
    id = this.id,
    userId = this.userId,
    returnProbability = this.returnProbability,

    )