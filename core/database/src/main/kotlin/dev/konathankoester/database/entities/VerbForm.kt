package dev.konathankoester.database.entities

import androidx.room.TypeConverter
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class VerbForm(
    val base: String,
    val pastSimple: String,
    val pastParticiple: String,
    val gerund: String,
    val thirdPersonSingular: String
)

internal class VerbFormConverter {

    @TypeConverter
    fun verbFormToString(verbForm: VerbForm?): String? =
        verbForm?.let { Json.encodeToString(VerbForm.serializer(), it) }

    @TypeConverter
    fun stringToVerbForm(string: String?): VerbForm? =
        string?.let { Json.decodeFromString(VerbForm.serializer(), string) }
}