package ru.tsu.data.net.accounts.socket

enum class TypeMessageHistorySocket(val typeName: String) {
    History("history"),
    NewOperation("newOperation"),
    ChangeOperationStatus("changeOperationStatus")
}
