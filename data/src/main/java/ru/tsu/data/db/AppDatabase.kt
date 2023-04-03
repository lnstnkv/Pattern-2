package ru.tsu.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.tsu.data.db.history.HistoryDao
import ru.tsu.data.db.history.HistoryEntity

@Database(
    entities = [
        HistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}
