package dev.konathankoester.ai_gemini.requests

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class GeminiGenerationConfig(
    val responseMimeType: String = "application/json",
    val responseSchema: JsonObject
)
