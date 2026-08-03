package dev.konathankoester.model

enum class WordSenseStatus { PENDING, READY, FAILED }

data class WordSenseDataModel(
    val id: String,
    val wordId: String,
    val partOfSpeech: String?,
    val definition: String?,
    val definitionSimple: String?,
    val synonyms: List<String>,
    val antonyms: List<String>,
    val verbForms: Map<String, String>?,
    val exampleSentences: List<String>,
    val generatedFromContext: String?,
    val modelVersion: String?,
    val status: WordSenseStatus,
    val generatedAt: Long?,
)
