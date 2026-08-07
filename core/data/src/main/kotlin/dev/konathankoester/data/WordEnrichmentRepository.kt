package dev.konathankoester.data

interface WordEnrichmentRepository {
    suspend fun enrichWord(word: String, contextSentence: String?, apiKey: String)
    suspend fun configureAiClient(apiKey: String)
}