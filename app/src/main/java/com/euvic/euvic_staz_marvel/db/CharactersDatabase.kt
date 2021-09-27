package com.euvic.euvic_staz_marvel.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.euvic.euvic_staz_marvel.db.models.characters.dto.CharactersResultDTO
import com.euvic.euvic_staz_marvel.db.models.series.SeriesResult
import com.euvic.euvic_staz_marvel.utils.DataConverter

@Database(entities = [CharactersResultDTO::class, SeriesResult::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class CharactersDatabase: RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
    abstract fun seriesDao(): SeriesDao

    companion object {
        @Volatile
        private var INSTANCE: CharactersDatabase? = null
        private const val DB_NAME = "characters.db"

        fun getDatabase(context: Context): CharactersDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CharactersDatabase::class.java,
                    DB_NAME
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}