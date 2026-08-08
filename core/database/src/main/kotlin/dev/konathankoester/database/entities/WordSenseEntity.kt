package dev.konathankoester.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

@Entity(
    tableName = "word_sense",
    foreignKeys = [
        ForeignKey(
            entity = WordEntity::class,
            parentColumns = ["id"],
            childColumns = ["wordId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["wordId"])]
)
data class WordSenseEntity(
    val wordId: String,
    @PrimaryKey
    val id: String,
    val partOfSpeech: String?,
    val definition: String?,
    val definitionSimple: String?,
    val synonyms: List<String>,
    val antonyms: List<String>,
    val verbForms: VerbForm?,
    val exampleSentences: List<String>,
    val generatedFromContext: String?,
    val modelVersion: String?,
    val status: WordSenseStatus,
    val generatedAt: Long?,
)

internal class StringListTypeConverter {

    @TypeConverter
    fun strListToStr(value: List<String>) =
        value.let { Json.encodeToString(ListSerializer(String.serializer()), it) }

    @TypeConverter
    fun strToStrList(str: String?): List<String> =
        str?.let { Json.decodeFromString(ListSerializer(String.serializer()), it) } ?: emptyList()
}
