package dev.konathankoester.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word")
data class WordEntity(
    @PrimaryKey
    val id: String,
    val text: String,
    val language: String,
    val createdAt: Long,
)
