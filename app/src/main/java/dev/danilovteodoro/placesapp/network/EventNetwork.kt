package dev.danilovteodoro.placesapp.network

import com.google.gson.annotations.SerializedName
import java.util.*

data class EventNetwork(
    @SerializedName("people")
    val people: List<Any?>,
    @SerializedName("date")
    val date: Date,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("price")
    val price: Double,
    @SerializedName("title")
    val title: String,
    @SerializedName("id")
    val id: String
)
