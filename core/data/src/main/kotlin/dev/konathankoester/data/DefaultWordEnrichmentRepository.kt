package dev.konathankoester.data

import dev.konathankoester.ai_gemini.GeminiWordEnrichmentClient
import dev.konathankoester.ai_gemini.util.GeminiResult
import dev.konathankoester.database.dao.HighlightDao
import dev.konathankoester.database.dao.WordDao
import dev.konathankoester.database.dao.WordSenseDao
import dev.konathankoester.database.entities.HighlightEntity
import dev.konathankoester.database.entities.WordEntity
import dev.konathankoester.database.entities.WordSenseStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.UUID
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

internal class DefaultWordEnrichmentRepository(
    private val aiClient: GeminiWordEnrichmentClient,
    private val wordSenseDao: WordSenseDao,
    private val wordDao: WordDao,
    private val highlightDao: HighlightDao,
    private val dispatcher: CoroutineDispatcher,
) : WordEnrichmentRepository {

    override suspend fun configureAiClient(apiKey: String) {
        aiClient.configure(apiKey)
    }

    override suspend fun enrichWord(wordId: String) = withContext(dispatcher) {
        val wordWithContextSentence = wordDao.getWithLatestContextSentence(wordId)
            ?: throw IllegalStateException("No word found for id $wordId")
        val word = wordWithContextSentence.wordEntity.text
        val contextSentence = wordWithContextSentence.latestContextSentence

        val existing = wordSenseDao.getByWordId(wordId)
        if (existing?.status == WordSenseStatus.READY) return@withContext

        val entityId = existing?.id ?: UUID.randomUUID().toString()

        when (val result = aiClient.enrichWord(word, contextSentence)) {
            is GeminiResult.Success -> {
                wordSenseDao.insert(
                    result.data.toEntity(wordId, entityId, result.modelVersion, contextSentence)
                )
            }
            is GeminiResult.RateLimited,
            is GeminiResult.NotRetryable,
            is GeminiResult.Retryable -> {
                wordSenseDao.insert(failedWordSense(entityId, wordId, contextSentence))
            }
        }
    }

    // TODO remove
    @OptIn(ExperimentalTime::class)
    override suspend fun seedTestWord(word: String, contextSentence: String): String =
        withContext(dispatcher) {
            val now = Clock.System.now().toEpochMilliseconds()
            val wordId = UUID.randomUUID().toString()
            wordDao.addWord(
                WordEntity(
                    id = wordId,
                    text = word,
                    language = "en",
                    createdAt = now,
                )
            )
            highlightDao.insert(
                HighlightEntity(
                    id = UUID.randomUUID().toString(),
                    wordId = wordId,
                    sourceSentence = contextSentence,
                    sourceRef = null,
                    resolvedSenseId = null,
                    createdAt = now,
                )
            )
            wordId
        }
}
