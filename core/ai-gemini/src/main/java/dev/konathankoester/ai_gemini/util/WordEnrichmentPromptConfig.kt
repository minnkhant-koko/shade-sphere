package dev.konathankoester.ai_gemini

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject


@Serializable
data class WordEnrichmentPromptConfig(
    val promptVersion: Int,
    val instructionTemplate: String,
    val contextLineTemplate: String,
    val responseSchema: JsonObject
)