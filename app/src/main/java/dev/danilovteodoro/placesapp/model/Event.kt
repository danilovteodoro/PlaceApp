package dev.danilovteodoro.placesapp.model

import java.util.*

data class Event(
    val people: List<Any?>,
    val date: Date,
    val description: String,
    val image: String,
    val longitude: Double,
    val latitude: Double,
    val price: Double,
    val title: String,
    val id: String
)