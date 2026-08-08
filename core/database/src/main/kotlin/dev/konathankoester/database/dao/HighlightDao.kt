package dev.konathankoester.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.konathankoester.database.entities.HighlightEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HighlightDao {

    @Query("SELECT * FROM highlight WHERE wordId = :wordId ORDER BY createdAt DESC")
    fun observeByWordId(wordId: String): Flow<List<HighlightEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: HighlightEntity)

    @Query("DELETE FROM highlight WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM highlight")
    suspend fun clear()
}
