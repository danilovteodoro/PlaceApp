package dev.danilovteodoro.placesapp.network

import com.google.gson.annotations.SerializedName

data class CheckInNetwork(
    @SerializedName("eventId")
    val eventId:String,
    @SerializedName("name")
    val name:String,
    @SerializedName("email")
    val email:String
)