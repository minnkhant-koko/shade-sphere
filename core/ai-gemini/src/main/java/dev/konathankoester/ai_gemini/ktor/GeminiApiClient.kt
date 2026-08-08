package dev.konathankoester.ai_gemini.ktor

import dev.konathankoester.ai_gemini.requests.GeminiGenerateContentRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class GeminiApiClient(
    private val httpClient: HttpClient,
    private val apiKey: String
) {
    suspend fun generate(
        request: GeminiGenerateContentRequest,
        model: String,
        generationMethod: String = "generateContent"
    ): HttpResponse {
        return httpClient.post("https://generativelanguage.googleapis.com/v1beta/models/$model:$generationMethod") {
            url { parameters.append("key", apiKey) }
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }
}