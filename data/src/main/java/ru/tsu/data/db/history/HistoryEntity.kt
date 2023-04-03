package ru.tsu.data.db.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.tsu.domain.account.model.AccountHistoryModelInvariant

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_history")
    val historyId: Long? = null,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "amount_of_money")
    val amountOfMoney: Float,
    @ColumnInfo(name = "account_id")
    val accountId: String,
    @ColumnInfo(name = "owner_id")
    val ownerId: String,
) {
    fun toDomain() = AccountHistoryModelInvariant(
        type = type,
        date = date,
        amountOfMoney = amountOfMoney,
        accountId = accountId,
        ownerId = ownerId,
    )

    companion object {
        fun fromDomain(modelInvariant: AccountHistoryModelInvariant) = HistoryEntity(
            type = modelInvariant.type,
            date = modelInvariant.date,
            amountOfMoney = modelInvariant.amountOfMoney,
            accountId = modelInvariant.accountId,
            ownerId = modelInvariant.ownerId,
        )
    }
}
