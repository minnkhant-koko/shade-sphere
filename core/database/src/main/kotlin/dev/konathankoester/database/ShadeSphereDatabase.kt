package dev.konathankoester.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.konathankoester.database.dao.WordSenseDao
import dev.konathankoester.database.entities.VerbFormConverter
import dev.konathankoester.database.entities.WordSenseEntity
import dev.konathankoester.database.entities.WordSenseStatusConverter

@Database(
    entities = [WordSenseEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(VerbFormConverter::class, WordSenseStatusConverter::class)
abstract class ShadeSphereDatabase : RoomDatabase() {

    abstract fun wordSenseDao(): WordSenseDao

    companion object {
        fun getAppDatabase(applicationContext: Context) = Room.databaseBuilder(
            applicationContext,
            ShadeSphereDatabase::class.java, "shade-sphere-db"
        )
            .build()
    }
}