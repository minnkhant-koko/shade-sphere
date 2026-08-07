package dev.konathankoester.data

import dev.konathankoester.ai_gemini.GeminiWordEnrichmentClient

internal class DefaultWordEnrichmentRepository(
    private val aiClient: GeminiWordEnrichmentClient
) : WordEnrichmentRepository {

    override suspend fun configureAiClient(apiKey: String) {
        aiClient.configure(apiKey)
    }

    override suspend fun enrichWord(word: String, contextSentence: String?, apiKey: String) {
        aiClient.enrichWord(word, contextSentence)
    }
}