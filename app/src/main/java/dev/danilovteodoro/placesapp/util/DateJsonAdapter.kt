package dev.danilovteodoro.placesapp.util

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.util.*

class DateJsonAdapter:TypeAdapter<Date>() {

    override fun write(out: JsonWriter?, value: Date?) {
        out?.let { out->
            if(value == null){
                out.nullValue()
            }else {
                out.value(value.time/1000)
            }
        }
    }

    override fun read(input: JsonReader?): Date? {
        return  input?.let { inp ->
                  Date(inp.nextLong() * 1000)
        }
    }
}