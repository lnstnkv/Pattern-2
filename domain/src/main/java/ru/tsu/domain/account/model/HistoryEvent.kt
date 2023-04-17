package ru.tsu.domain.account.model

sealed class HistoryEvent {

    data class OperationsModel(
        val operations: List<HistoryModel>,
        val totalCount: Int,
    ) : HistoryEvent()

    data class HistoryModel(
        val id: String,
        val type: String,
        val date: String,
        val callerId: String,
        val payload: PayloadHistory,
        val status: OperationStatus,
    ) : HistoryEvent()

    data class ChangeOperationStatusModel(
        val id: String,
        val status: OperationStatus,
    ) : HistoryEvent()
}


enum class OperationStatus {
    Pending,
    Rejected,
    Accepted
}
