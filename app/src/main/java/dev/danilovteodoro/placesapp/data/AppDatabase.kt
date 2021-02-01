package dev.danilovteodoro.placesapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.danilovteodoro.placesapp.data.dao.EventDao

@Database(
    entities = [EventData::class],
    version = 1
)
abstract class AppDatabase():RoomDatabase() {
    companion object{
        const val DB_NAME="placeDatabase"
    }

    abstract fun eventDao(): EventDao
}