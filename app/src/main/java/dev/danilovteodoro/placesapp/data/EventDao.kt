package dev.danilovteodoro.placesapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(eventData: EventData)

    @Query("SELECT * FROM events where id = :eventId")
    suspend fun get(eventId:String) : EventData

    @Query("SELECT * from events")
    suspend fun list():List<EventData>
}