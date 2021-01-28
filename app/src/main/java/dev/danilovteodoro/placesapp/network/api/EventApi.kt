package dev.danilovteodoro.placesapp.network.api

import dev.danilovteodoro.placesapp.network.EventNetwork
import retrofit2.http.GET
import retrofit2.http.Path

interface EventApi {

    @GET("events")
   suspend fun getEvents():List<EventNetwork>

   @GET("events/{id}")
   suspend fun getEventById(@Path("id")id:String):EventNetwork
}