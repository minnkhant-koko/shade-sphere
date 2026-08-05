package dev.konathankoester.ai_gemini.response

import kotlinx.serialization.Serializable

@Serializable
data class GeminiPromptTokenDetail(
    val modality: String,
    val tokenCount: Int
)