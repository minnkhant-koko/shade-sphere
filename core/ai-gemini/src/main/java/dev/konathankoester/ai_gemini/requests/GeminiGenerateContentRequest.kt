package dev.konathankoester.ai_gemini.requests

import kotlinx.serialization.Serializable

@Serializable
data class GeminiGenerateContentRequest(
    val contents: List<GeminiRequestContent>,
    val generationConfig: GeminiGenerationConfig
)
