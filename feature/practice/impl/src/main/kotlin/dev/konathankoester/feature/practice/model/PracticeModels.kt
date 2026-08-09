package dev.konathankoester.feature.practice.model

import dev.konathankoester.design.component.PracticeCardType
import dev.konathankoester.design.component.WordStatus

data class PracticeQuestion(
    val type: PracticeCardType,
    val prompt: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
)

data class WordProgress(
    val word: String,
    val from: WordStatus,
    val to: WordStatus,
)

data class SessionUiState(
    val questions: List<PracticeQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedAnswer: Int? = null,
    val isComplete: Boolean = false,
) {
    val current get() = questions.getOrNull(currentIndex)
    val progress get() = if (questions.isEmpty()) 0f else currentIndex.toFloat() / questions.size
    val hasAnswered get() = selectedAnswer != null
}

data class ResultsUiState(
    val correct: Int = 0,
    val total: Int = 0,
    val durationMinutes: Int = 0,
    val improved: List<WordProgress> = emptyList(),
) {
    val missed get() = total - correct
    val scoreFraction get() = if (total > 0) correct.toFloat() / total else 0f
}
