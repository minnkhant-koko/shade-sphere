package dev.konathankoester.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.konathankoester.database.entities.UserWordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserWordDao {

    @Query("SELECT * FROM user_word WHERE wordId = :wordId")
    fun observeByWordId(wordId: String): Flow<UserWordEntity?>

    @Query("SELECT * FROM user_word")
    fun observeAll(): Flow<List<UserWordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: UserWordEntity)

    @Update
    suspend fun update(entity: UserWordEntity)

    @Query("DELETE FROM user_word WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM user_word")
    suspend fun clear()
}
