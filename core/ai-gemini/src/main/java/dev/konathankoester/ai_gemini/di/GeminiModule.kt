package dev.konathankoester.ai_gemini.di

import dev.konathankoester.ai_gemini.DefaultGeminiWordEnrichmentClient
import dev.konathankoester.ai_gemini.GeminiWordEnrichmentClient
import dev.konathankoester.ai_gemini.GeminiWordEnrichmentRequestBuilder
import dev.konathankoester.ai_gemini.mappers.GeminiWordEnrichmentResponseMapper
import dev.konathankoester.ai_gemini.WordEnrichmentPromptLoader
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val geminiModule = module {

    single {
        Json { ignoreUnknownKeys = true }
    }

    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(get())
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 30_000
            }
        }
    }

    single {
        WordEnrichmentPromptLoader(json = get())
    }

    single {
        GeminiWordEnrichmentRequestBuilder(promptLoader = get())
    }

    single {
        GeminiWordEnrichmentResponseMapper(json = get())
    }

    single<GeminiWordEnrichmentClient> {
        DefaultGeminiWordEnrichmentClient(
            httpClient = get(),
            requestBuilder = get(),
            responseMapper = get()
        )
    }
}