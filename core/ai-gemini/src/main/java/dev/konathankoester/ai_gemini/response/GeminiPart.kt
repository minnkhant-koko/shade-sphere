package dev.konathankoester.ai_gemini.response

import kotlinx.serialization.Serializable

@Serializable
data class GeminiPart(
    val text: String? = null,
    val thoughtSignature: String? = null
)
