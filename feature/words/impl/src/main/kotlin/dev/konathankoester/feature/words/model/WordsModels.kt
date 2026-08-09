package dev.konathankoester.feature.words.model

import dev.konathankoester.design.component.WordStatus

data class WordEntry(
    val id: String,
    val word: String,
    val gloss: String,
    val status: WordStatus,
    val source: String,
)

data class WordsUiState(
    val newCount: Int = 0,
    val learningCount: Int = 0,
    val masteredCount: Int = 0,
    val dueCount: Int = 0,
    val hasEnrichmentError: Boolean = false,
    val enrichmentErrorCount: Int = 0,
    val recentWords: List<WordEntry> = emptyList(),
    val allWords: List<WordEntry> = emptyList(),
)
