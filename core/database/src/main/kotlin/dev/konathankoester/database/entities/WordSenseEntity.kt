package dev.konathankoester.database.entities

import androidx.room.Entity

@Entity(tableName = "word_sense")
data class WordSenseEntity(
    val wordId: String,
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
