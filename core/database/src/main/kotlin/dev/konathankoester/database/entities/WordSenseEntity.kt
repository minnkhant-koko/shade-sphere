package dev.konathankoester.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "word_sense",
    foreignKeys = [
        ForeignKey(
            entity = WordEntity::class,
            parentColumns = ["id"],
            childColumns = ["wordId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["wordId"])]
)
data class WordSenseEntity(
    val wordId: String,
    @PrimaryKey
    val id: String,
    val partOfSpeech: String?,
    val definition: String?,
    val definitionSimple: String?,
    val synonyms: List<String>,
    val antonyms: List<String>,
    val verbForms: VerbForm?,
    val exampleSentences: List<String>,
    val generatedFromContext: String?,
    val modelVersion: String?,
    val status: WordSenseStatus,
    val generatedAt: Long?,
)
