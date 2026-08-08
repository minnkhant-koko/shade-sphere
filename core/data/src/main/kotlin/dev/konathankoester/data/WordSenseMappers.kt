package dev.konathankoester.data

import dev.konathankoester.ai_gemini.VerbForms
import dev.konathankoester.ai_gemini.WordEnrichmentResponse
import dev.konathankoester.database.entities.VerbForm
import dev.konathankoester.database.entities.WordSenseEntity
import dev.konathankoester.database.entities.WordSenseStatus
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
internal fun WordEnrichmentResponse.toEntity(
    wordId: String,
    id: String,
    modelVersion: String,
    contextSentence: String?,
) = WordSenseEntity(
    id = id,
    wordId = wordId,
    partOfSpeech = partOfSpeech,
    definition = definition,
    definitionSimple = definitionSimple,
    synonyms = synonyms,
    antonyms = antonyms,
    verbForms = verbForms?.toEntity(),
    exampleSentences = exampleSentences,
    generatedFromContext = contextSentence,
    modelVersion = modelVersion,
    status = WordSenseStatus.READY,
    generatedAt = Clock.System.now().toEpochMilliseconds(),
)

internal fun failedWordSense(
    id: String,
    wordId: String,
    contextSentence: String?,
) = WordSenseEntity(
    id = id,
    wordId = wordId,
    partOfSpeech = null,
    definition = null,
    definitionSimple = null,
    synonyms = emptyList(),
    antonyms = emptyList(),
    verbForms = null,
    exampleSentences = emptyList(),
    generatedFromContext = contextSentence,
    modelVersion = null,
    status = WordSenseStatus.FAILED,
    generatedAt = null,
)

internal fun VerbForms.toEntity() = VerbForm(
    base = base,
    pastSimple = pastSimple,
    pastParticiple = pastParticiple,
    gerund = gerund,
    thirdPersonSingular = thirdPersonSingular,
)
