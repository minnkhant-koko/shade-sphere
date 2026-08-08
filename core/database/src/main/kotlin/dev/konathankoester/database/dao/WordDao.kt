package dev.konathankoester.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.konathankoester.database.entities.WordEntity
import dev.konathankoester.database.relations.WordWithContextSentence
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("select * from word where id = :id")
    fun getWordByWordId(id: String): Flow<WordEntity?>

    @Query("select * from word")
    fun getWords(): Flow<List<WordEntity>?>

    @Query(
        """
        SELECT word.*, highlight.sourceSentence AS latestContextSentence
        FROM word
        LEFT JOIN highlight ON highlight.wordId = word.id
        WHERE word.id = :wordId
        ORDER BY highlight.createdAt DESC
        LIMIT 1
    """
    )
    suspend fun getWithLatestContextSentence(wordId: String): WordWithContextSentence?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWord(word: WordEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWord(words: List<WordEntity>)

    @Query("delete from word")
    fun clearWords()

    @Query("delete from word where id = :id")
    fun clearWordById(id: String)
}