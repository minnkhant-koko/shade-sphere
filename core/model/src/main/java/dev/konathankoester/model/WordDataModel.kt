package dev.konathankoester.model

data class WordDataModel(
    val id: String,
    val text: String,
    val language: String,
    val createdAt: Long,
)
