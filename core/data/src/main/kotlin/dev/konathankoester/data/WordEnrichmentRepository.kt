package dev.konathankoester.data

interface WordEnrichmentRepository {
    suspend fun enrichWord(wordId: String)
    suspend fun configureAiClient(apiKey: String)
    // TODO remove
    suspend fun seedTestWord(word: String, contextSentence: String): String
}
