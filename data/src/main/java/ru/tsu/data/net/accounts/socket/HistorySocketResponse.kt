package ru.tsu.data.net.accounts.socket

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.tsu.data.net.accounts.PayloadHistoryData
import ru.tsu.domain.account.model.HistoryEvent
import ru.tsu.domain.account.model.OperationStatus

sealed class HistorySocketResponse {

    abstract fun toDomain(): HistoryEvent

    @Serializable
    data class HistoryDto(
        @SerialName("operations")
        val operations: List<AccountHistoryResponseDto>,
        @SerialName("totalCount")
        val totalCount: Int,
    ) : HistorySocketResponse() {

        override fun toDomain(): HistoryEvent.OperationsModel = HistoryEvent.OperationsModel(
            operations = this.operations.map { it.toDomain() },
            totalCount = this.totalCount
        )
    }

    @Serializable
    data class AccountHistoryResponseDto(
        @SerialName("id")
        val id: String,
        @SerialName("type")
        val type: String,
        @SerialName("date")
        val date: String,
        @SerialName("callerId")
        val callerId: String,
        @SerialName("payload")
        val payload: PayloadHistoryData,
        @SerialName("status")
        val status: OperationStatusDto,
    ) : HistorySocketResponse() {

        override fun toDomain(): HistoryEvent.HistoryModel = HistoryEvent.HistoryModel(
            id = id,
            type = type,
            date = date,
            callerId = callerId,
            payload = payload.toDomain(),
            status = status.toDomain(),
        )
    }

    @Serializable
    data class ChangeOperationStatusDto(
        @SerialName("id")
        val id: String,
        @SerialName("status")
        val status: OperationStatusDto,
    ) : HistorySocketResponse() {

        override fun toDomain(): HistoryEvent.ChangeOperationStatusModel = HistoryEvent.ChangeOperationStatusModel(
            id = id,
            status = status.toDomain(),
        )
    }
}

@Serializable
enum class OperationStatusDto {
    @SerialName("pending")
    Pending,

    @SerialName("rejected")
    Rejected,

    @SerialName("accepted")
    Accepted;

    fun toDomain(): OperationStatus = when (this) {
        Pending -> OperationStatus.Pending
        Rejected -> OperationStatus.Rejected
        Accepted -> OperationStatus.Accepted
    }
}
