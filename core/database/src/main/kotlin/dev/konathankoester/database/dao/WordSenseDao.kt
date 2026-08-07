package dev.konathankoester.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.konathankoester.database.entities.WordSenseEntity
import kotlinx.coroutines.flow.Flow

interface WordSenseDao {

    @Query("SELECT * FROM word_sense")
    fun get(): Flow<List<WordSenseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entities: List<WordSenseEntity>)

    @Query("DELETE FROM word_sense")
    suspend fun clear()
}