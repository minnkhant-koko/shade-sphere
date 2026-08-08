package dev.konathankoester.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

enum class SrsState { NEW, LEARNING, REVIEWING, MASTERED }

@Entity(
    tableName = "user_word",
    foreignKeys = [
        ForeignKey(
            entity = WordEntity::class,
            parentColumns = ["id"],
            childColumns = ["wordId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["wordId"], unique = true)]
)
data class UserWordEntity(
    @PrimaryKey
    val id: String,
    val wordId: String,
    val firstAddedAt: Long,
    val srsState: SrsState,
    val nextReviewAt: Long?,
    val easeFactor: Float,
    val interval: Int,
)

class SrsStateTypeConverter {

    @TypeConverter
    fun srsStateToString(value: SrsState) = value.name

    @TypeConverter
    fun stringToSrsState(string: String) = SrsState.valueOf(string)
}
