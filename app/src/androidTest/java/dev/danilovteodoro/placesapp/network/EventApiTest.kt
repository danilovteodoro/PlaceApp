package dev.danilovteodoro.placesapp.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.danilovteodoro.placesapp.di.NetworkModule
import dev.danilovteodoro.placesapp.network.api.EventApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.Test
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class EventApiTest {
    private lateinit var eventApi: EventApi

    @Before
    fun setup(){
        val gson =  NetworkModule.providesGson()
        val retrofit = NetworkModule.providesRetrofit(gson)
        eventApi = NetworkModule.providesEventApi(retrofit)
    }

    @Test
    fun testGetEvents() = runBlocking{
        val events = eventApi.getEvents()
           assertNotNull(events)
    }

    @Test
    fun testGetEventById() = runBlocking {
        val event = eventApi.getEventById("1")
        assertNotNull(event)
    }


}