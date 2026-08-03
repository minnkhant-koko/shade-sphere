package dev.konathankoester.model

data class HighlightDataModel(
    val id: String,
    val wordId: String,
    val sourceSentence: String,
    val sourceRef: String?,
    val resolvedSenseId: String?,
    val createdAt: Long,
)
