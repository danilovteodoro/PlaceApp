package dev.danilovteodoro.placesapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dev.danilovteodoro.placesapp.util.DateRoomConverter
import java.util.*

@Entity(tableName = "events")
@TypeConverters(DateRoomConverter::class)
data class EventData(
    @PrimaryKey
    val id: String,
    val date: Date,
    val description: String,
    val image: String,
    val longitude: Double,
    val latitude: Double,
    val price: Double,
    val title: String

)