package dev.konathankoester.ai_gemini

import kotlinx.serialization.Serializable

@Serializable
data class WordEnrichmentResponse(
    val word: String,
    val partOfSpeech: String,
    val definition: String,
    val definitionSimple: String,
    val synonyms: List<String> = emptyList(),
    val antonyms: List<String> = emptyList(),
    val verbForms: VerbForms? = null,
    val exampleSentences: List<String> = emptyList()
)