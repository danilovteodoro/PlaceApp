package dev.danilovteodoro.placesapp.util

import androidx.room.TypeConverter
import java.util.*

class DateRoomConverter {

    @TypeConverter
    fun toDate(dateLong:Long):Date{
        return Date(dateLong)
    }

    @TypeConverter
    fun fromDate(date: Date):Long{
        return date.time
    }
}