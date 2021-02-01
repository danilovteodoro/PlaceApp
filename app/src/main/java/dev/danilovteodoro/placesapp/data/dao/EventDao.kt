package dev.danilovteodoro.placesapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.danilovteodoro.placesapp.data.EventData

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(eventData: EventData)

    @Query("SELECT * FROM events where id = :eventId")
    suspend fun get(eventId:String) : EventData

    @Query("SELECT * from events ORDER BY id")
    suspend fun list():List<EventData>
}