package dev.konathankoester.ai_gemini

import dev.konathankoester.ai_gemini.util.GeminiResult

interface GeminiWordEnrichmentClient {
    fun configure(apiKey: String)
    suspend fun enrichWord(
        word: String,
        contextSentence: String?
    ): GeminiResult<WordEnrichmentResponse>
}