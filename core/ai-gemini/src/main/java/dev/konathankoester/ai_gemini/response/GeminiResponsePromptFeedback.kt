package dev.konathankoester.ai_gemini.response

import kotlinx.serialization.Serializable

@Serializable
data class GeminiPromptFeedback(
    val blockReason: String? = null
)