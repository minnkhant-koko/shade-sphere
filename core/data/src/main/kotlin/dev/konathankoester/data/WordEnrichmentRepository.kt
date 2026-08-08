package dev.konathankoester.data

interface WordEnrichmentRepository {
    suspend fun enrichWord(wordId: String)
    suspend fun configureAiClient(apiKey: String)
}