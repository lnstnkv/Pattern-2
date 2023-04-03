package ru.tsu.data.db.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert
    fun insertHistory(entity: HistoryEntity): Long

    @Query(
        """
        SELECT * FROM history ORDER BY id_history DESC
    """
    )
    fun getHistory(): Flow<List<HistoryEntity>>
}
