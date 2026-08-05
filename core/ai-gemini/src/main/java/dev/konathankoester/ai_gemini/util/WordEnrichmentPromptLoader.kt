package dev.konathankoester.ai_gemini

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

internal class WordEnrichmentPromptLoader(
    private val json: Json = Json { ignoreUnknownKeys = true }
) {
    private var config: WordEnrichmentPromptConfig? = null
    private val mutex = Mutex()

    private suspend fun loadedConfig(): WordEnrichmentPromptConfig = mutex.withLock {
        config ?: loadFromDisk().also { config = it }
    }

    private suspend fun loadFromDisk(): WordEnrichmentPromptConfig = withContext(Dispatchers.IO) {
        val stream = javaClass.classLoader?.getResourceAsStream("prompts/word_enrichment.json")
            ?: error("word_enrichment.json not found on classpath")
        val text = stream.bufferedReader().use { it.readText() }
        json.decodeFromString(WordEnrichmentPromptConfig.serializer(), text)
    }

    suspend fun buildInstruction(word: String, contextSentence: String?): String {
        val config = loadedConfig()
        val contextLine = if (contextSentence != null) {
            config.contextLineTemplate.replace("{context}", contextSentence)
        } else {
            ""
        }
        return config.instructionTemplate
            .replace("{word}", word)
            .replace("{contextLine}", contextLine)
    }

    suspend fun responseSchema() = loadedConfig().responseSchema
}