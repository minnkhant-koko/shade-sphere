package dev.konathankoester.database.relations

import androidx.room.Embedded
import dev.konathankoester.database.entities.WordEntity

data class WordWithContextSentence(
    @Embedded val wordEntity: WordEntity,
    val latestContextSentence: String?
)