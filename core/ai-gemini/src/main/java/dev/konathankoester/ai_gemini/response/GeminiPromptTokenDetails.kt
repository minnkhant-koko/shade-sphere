package dev.konathankoester.ai_gemini.response

import kotlinx.serialization.Serializable

@Serializable
data class GeminiPromptTokenDetails(
    val details: List<GeminiPromptTokenDetail>
)