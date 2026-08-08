package dev.konathankoester.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "highlight",
    foreignKeys = [
        ForeignKey(
            entity = WordEntity::class,
            parentColumns = ["id"],
            childColumns = ["wordId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("wordId")]
)
data class HighlightEntity(
    val id: String,
    val wordId: String,
    val sourceSentence: String,
    val sourceRef: String?,
    val resolvedSenseId: String?,
    val createdAt: Long,
)