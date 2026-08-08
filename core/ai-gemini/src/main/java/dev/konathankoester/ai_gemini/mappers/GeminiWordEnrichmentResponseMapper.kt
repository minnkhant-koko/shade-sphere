package dev.konathankoester.ai_gemini.mappers

import dev.konathankoester.ai_gemini.WordEnrichmentResponse
import dev.konathankoester.ai_gemini.response.GeminiGenerateContentResponse
import dev.konathankoester.ai_gemini.util.GeminiResult
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

internal class GeminiWordEnrichmentResponseMapper(
    private val json: Json = Json { ignoreUnknownKeys = true }
) {

    private val safetyOrPolicyFinishReasons = setOf(
        "SAFETY",
        "RECITATION",
        "LANGUAGE",
        "BLOCKLIST",
        "PROHIBITED_CONTENT",
        "SPII"
    )

    suspend fun map(
        response: HttpResponse,
        modelVersion: String
    ): GeminiResult<WordEnrichmentResponse> {
        return try {
            when (response.status) {
                HttpStatusCode.OK -> response.mapSuccessResponse(modelVersion)
                HttpStatusCode.TooManyRequests -> GeminiResult.RateLimited(
                    retryAfterSeconds = response.headers[HttpHeaders.RetryAfter]?.toIntOrNull(),
                    modelVersion
                )

                HttpStatusCode.Unauthorized, HttpStatusCode.Forbidden -> GeminiResult.NotRetryable(
                    reason = "Auth error: ${response.status.description}",
                    modelVersion
                )

                else -> GeminiResult.Retryable(
                    reason = "HTTP ${response.status.value}: ${response.status.description}",
                    null,
                    modelVersion
                )
            }
        } catch (e: Exception) {
            GeminiResult.Retryable(reason = e.message ?: "Unknown error", cause = e, modelVersion)
        }
    }

    private suspend fun HttpResponse.mapSuccessResponse(modelVersion: String): GeminiResult<WordEnrichmentResponse> {
        val envelope: GeminiGenerateContentResponse = body()

        // Step 3: whole prompt blocked, no candidates at all
        if (envelope.promptFeedback?.blockReason != null) {
            return GeminiResult.NotRetryable(
                reason = "Prompt blocked: ${envelope.promptFeedback.blockReason}",
                modelVersion
            )
        }

        // Step 4: defensively check candidates exist
        val candidate = envelope.candidates.firstOrNull()
            ?: return GeminiResult.Retryable(reason = "No candidates returned", null, modelVersion)

        // Step 5: check finishReason
        when (candidate.finishReason) {
            "STOP" -> Unit // proceed to parse
            in safetyOrPolicyFinishReasons -> return GeminiResult.NotRetryable(
                reason = "Content filtered: ${candidate.finishReason}", modelVersion
            )

            else -> return GeminiResult.Retryable(
                reason = "Unexpected finishReason: ${candidate.finishReason}", null, modelVersion
            )
        }

        // Step 6: extract and parse the inner JSON payload
        val innerText = candidate.content?.parts?.firstOrNull()?.text
            ?: return GeminiResult.Retryable(
                reason = "No text content in response",
                null,
                modelVersion
            )

        val enrichment = try {
            json.decodeFromString(WordEnrichmentResponse.serializer(), innerText)
        } catch (e: SerializationException) {
            return GeminiResult.Retryable(
                reason = "Malformed JSON payload",
                cause = e,
                modelVersion
            )
        }

        return GeminiResult.Success(
            data = enrichment,
            modelVersion = modelVersion,
            usage = envelope.usageMetadata
        )
    }
}