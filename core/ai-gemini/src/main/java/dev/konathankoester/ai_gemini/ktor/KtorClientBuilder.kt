package dev.konathankoester.ai_gemini

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
}

val httpClient = HttpClient(OkHttp) {
    install(ContentNegotiation) {
        json(json)
    }
    install(HttpTimeout) {
        requestTimeoutMillis = 30_000
    }
}