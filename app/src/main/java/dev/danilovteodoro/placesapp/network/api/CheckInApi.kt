package dev.danilovteodoro.placesapp.network.api

import dev.danilovteodoro.placesapp.network.CheckInNetwork
import retrofit2.http.Body
import retrofit2.http.POST

interface CheckInApi {
    @POST("checkin")
    suspend  fun postCheckIn(@Body checkIn:CheckInNetwork)
}