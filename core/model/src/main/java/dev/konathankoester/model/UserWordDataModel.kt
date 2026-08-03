package dev.konathankoester.model

enum class SrsState { NEW, LEARNING, REVIEWING, MASTERED }

data class UserWordDataModel(
    val id: String,
    val wordId: String,
    val firstAddedAt: Long,
    val srsState: SrsState,
    val nextReviewAt: Long?,
    val easeFactor: Float,
    val interval: Int,
)
