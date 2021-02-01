package dev.danilovteodoro.placesapp.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.danilovteodoro.placesapp.data.dao.EventDao
import dev.danilovteodoro.placesapp.di.DatabaseModule
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Test
import java.util.*

@RunWith(AndroidJUnit4::class)
class EventDaoTest {

    private lateinit var eventDao: EventDao

    @Before
    fun setup(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        eventDao = DatabaseModule.provideEventDao(DatabaseModule.provideDatabase(context))
    }

    @Test
    fun testDao() = runBlocking{
        val eventData = EventData(
            "1",
            Date(),
            "Description",
            "http://testedeurl.com",
            -51.2146267,
            -30.0392981,
            50.0,
            "Title"
        )
        eventDao.insert(eventData)
        val events = eventDao.list()
        assertNotNull(events)
    }

}