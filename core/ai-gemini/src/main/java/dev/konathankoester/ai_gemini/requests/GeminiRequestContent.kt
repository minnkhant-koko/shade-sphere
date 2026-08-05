package dev.konathankoester.ai_gemini.requests

import dev.konathankoester.ai_gemini.response.GeminiPart
import kotlinx.serialization.Serializable

@Serializable
data class GeminiRequestContent(
    val role: String = "user",
    val parts: List<GeminiPart>
)
