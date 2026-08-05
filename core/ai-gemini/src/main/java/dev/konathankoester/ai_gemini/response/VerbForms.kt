package dev.konathankoester.ai_gemini

import kotlinx.serialization.Serializable

@Serializable
data class VerbForms(
    val base: String,
    val pastSimple: String,
    val pastParticiple: String,
    val gerund: String,
    val thirdPersonSingular: String
)