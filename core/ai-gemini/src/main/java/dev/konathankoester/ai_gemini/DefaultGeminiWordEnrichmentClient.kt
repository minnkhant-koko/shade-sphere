package dev.konathankoester.ai_gemini

import dev.konathankoester.ai_gemini.ktor.GeminiApiClient
import dev.konathankoester.ai_gemini.util.GeminiResult
import io.ktor.client.HttpClient
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class DefaultGeminiWordEnrichmentClient(
    private val httpClient: HttpClient,
    private val requestBuilder: GeminiWordEnrichmentRequestBuilder,
    private val responseMapper: GeminiWordEnrichmentResponseMapper
) : GeminiWordEnrichmentClient {

    private val mutex = Mutex()
    private var apiClient: GeminiApiClient? = null

    override fun configure(apiKey: String) {
        apiClient = GeminiApiClient(httpClient = httpClient, apiKey = apiKey)
    }

    override suspend fun enrichWord(
        word: String,
        contextSentence: String?
    ): GeminiResult<WordEnrichmentResponse> {
        val client = mutex.withLock {
            apiClient ?: throw IllegalStateException(
                "GeminiWordEnrichmentClient.configure(apiKey) must be called before enrichWord()"
            )
        }
        val request = requestBuilder.build(word, contextSentence)
        val response = client.generate(request)
        return responseMapper.map(response)
    }
}