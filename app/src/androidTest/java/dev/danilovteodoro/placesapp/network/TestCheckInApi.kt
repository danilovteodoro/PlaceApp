package dev.danilovteodoro.placesapp.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.danilovteodoro.placesapp.di.NetworkModule
import dev.danilovteodoro.placesapp.network.api.CheckInApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.HttpException
import org.junit.Assert.assertEquals

@RunWith(AndroidJUnit4::class)
class TestCheckInApi {
    private lateinit var checkInApi: CheckInApi

    @Before
    fun setup(){
        val gson =  NetworkModule.providesGson()
        val retrofit = NetworkModule.providesRetrofit(gson)
        checkInApi = NetworkModule.providesCheckInpi(retrofit)
    }

    @Test
    fun testPostCheckIn() = runBlocking {
       val checkIn = CheckInNetwork(
           "1",
           "Fulano de Tal",
           "fulano@email.com"
       )
       try {
           checkInApi.postCheckIn(checkIn)
       } catch (e:HttpException){
           if (e.code() == 400){
              val error = e.response()?.errorBody()?.string()
               assertEquals("\"Max number of elements reached for this resource!\"",error)
               return@runBlocking
           }
           throw e
       }   catch (e:Exception){
           throw e
       }
    }
}