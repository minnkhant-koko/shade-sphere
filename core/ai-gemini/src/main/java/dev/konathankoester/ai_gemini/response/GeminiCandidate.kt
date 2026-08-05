package dev.konathankoester.ai_gemini.response

import kotlinx.serialization.Serializable

@Serializable
data class GeminiCandidate(
    val content: GeminiContent? = null,
    val finishReason: String? = null,
    val index: Int = 0
)
