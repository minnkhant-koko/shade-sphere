package dev.konathankoester.ai_gemini.util

import dev.konathankoester.ai_gemini.response.GeminiUsageMetadata

sealed class GeminiResult<out T> {
    data class Success<T>(
        val data: T,
        val modelVersion: String?,
        val usage: GeminiUsageMetadata?
    ) : GeminiResult<T>()

    data class RateLimited(val retryAfterSeconds: Int?) : GeminiResult<Nothing>()

    data class Retryable(val reason: String, val cause: Throwable? = null) : GeminiResult<Nothing>()
    // network failure, timeout, malformed JSON, MAX_TOKENS, unknown/OTHER finishReason

    data class NotRetryable(val reason: String) : GeminiResult<Nothing>()
    // prompt blocked, SAFETY, RECITATION, BLOCKLIST, PROHIBITED_CONTENT, SPII, LANGUAGE
}