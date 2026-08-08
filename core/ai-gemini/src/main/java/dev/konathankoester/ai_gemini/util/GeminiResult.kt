package dev.konathankoester.ai_gemini.util

import dev.konathankoester.ai_gemini.response.GeminiUsageMetadata

sealed class GeminiResult<out T>(val modelVersion: String) {
    class Success<T>(
        val data: T,
        modelVersion: String,
        val usage: GeminiUsageMetadata?
    ) : GeminiResult<T>(modelVersion)

    class RateLimited(val retryAfterSeconds: Int?, modelVersion: String) :
        GeminiResult<Nothing>(modelVersion)

    class Retryable(val reason: String, val cause: Throwable? = null, modelVersion: String) :
        GeminiResult<Nothing>(modelVersion)
    // network failure, timeout, malformed JSON, MAX_TOKENS, unknown/OTHER finishReason

    class NotRetryable(val reason: String, modelVersion: String) :
        GeminiResult<Nothing>(modelVersion)
    // prompt blocked, SAFETY, RECITATION, BLOCKLIST, PROHIBITED_CONTENT, SPII, LANGUAGE
}