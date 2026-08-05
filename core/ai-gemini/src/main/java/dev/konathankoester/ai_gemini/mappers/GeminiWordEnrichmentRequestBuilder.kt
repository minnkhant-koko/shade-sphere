package dev.konathankoester.ai_gemini

import dev.konathankoester.ai_gemini.requests.GeminiGenerateContentRequest
import dev.konathankoester.ai_gemini.requests.GeminiGenerationConfig
import dev.konathankoester.ai_gemini.requests.GeminiRequestContent
import dev.konathankoester.ai_gemini.response.GeminiPart

internal class GeminiWordEnrichmentRequestBuilder(
    private val promptLoader: WordEnrichmentPromptLoader
) {
    suspend fun build(word: String, contextSentence: String?): GeminiGenerateContentRequest {
        val instruction = promptLoader.buildInstruction(word, contextSentence)
        val schema = promptLoader.responseSchema()

        val part = GeminiPart(text = instruction)
        val content = GeminiRequestContent(role = "user", parts = listOf(part))
        val generationConfig = GeminiGenerationConfig(responseSchema = schema)

        return GeminiGenerateContentRequest(
            listOf(content),
            generationConfig
        )
    }
}