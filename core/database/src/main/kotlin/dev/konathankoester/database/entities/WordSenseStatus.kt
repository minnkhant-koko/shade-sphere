package dev.konathankoester.database.entities

import androidx.room.TypeConverter

enum class WordSenseStatus { PENDING, READY, FAILED }

internal class WordSenseStatusConverter {

    @TypeConverter
    fun statusToString(status: WordSenseStatus) = status.name

    @TypeConverter
    fun stringToStatus(string: String) = WordSenseStatus.valueOf(string)
}