package dev.konathankoester.database.entities

import androidx.room.Entity

@Entity(tableName = "word")
data class WordEntity(
    val id: String,
    val text: String,
    val language: String,
    val createdAt: Long,
)
