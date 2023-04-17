package ru.tsu.domain.credits

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tsu.domain.FlowUseCase
import javax.inject.Inject

interface GetRatingUseCase : FlowUseCase<String, RatingModel>

class GetRatingUseCaseImpl @Inject constructor(private val creditDataSource: CreditDataSource) :
    GetRatingUseCase {
    override fun execute(param: String): Flow<Result<RatingModel>> = flow {
        val result = creditDataSource.getRating(param)
        emit(Result.success(result))
    }
}