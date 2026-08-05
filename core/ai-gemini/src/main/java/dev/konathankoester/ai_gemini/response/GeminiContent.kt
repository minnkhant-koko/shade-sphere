package dev.konathankoester.ai_gemini.response

import kotlinx.serialization.Serializable

@Serializable
data class GeminiContent(
    val parts: List<GeminiPart> = emptyList(),
    val role: String? = null
)
