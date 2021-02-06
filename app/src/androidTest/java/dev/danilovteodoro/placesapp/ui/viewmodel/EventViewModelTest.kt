package dev.danilovteodoro.placesapp.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.danilovteodoro.placesapp.data.mapper.EventDataMapper
import dev.danilovteodoro.placesapp.di.DatabaseModule
import dev.danilovteodoro.placesapp.di.NetworkModule
import dev.danilovteodoro.placesapp.network.mapper.CheckInMapper
import dev.danilovteodoro.placesapp.network.mapper.EventMapper
import dev.danilovteodoro.placesapp.repository.EventRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.lifecycle.Observer
import dev.danilovteodoro.placesapp.model.CheckIn
import dev.danilovteodoro.placesapp.model.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import org.hamcrest.CoreMatchers.*
import util.DataState
import org.junit.Assert.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class EventViewModelTest{
    private lateinit var viewModel: EventViewModel
            
    @Before
    fun setup(){
        val retrofit = NetworkModule.providesRetrofit(
            NetworkModule.providesGson()
        )
        val database = DatabaseModule.
        provideDatabase(ApplicationProvider.getApplicationContext())
        val repository = EventRepository(
            NetworkModule.providesEventApi(retrofit),
            EventMapper(),
            NetworkModule.providesCheckInpi(retrofit),
            CheckInMapper(),
            DatabaseModule.provideEventDao(database),
            EventDataMapper(),
            ApplicationProvider.getApplicationContext()
        )
        viewModel = EventViewModel(repository, SavedStateHandle())
    }

    @Test
    fun testGetEventsViewModel() {
        val observer = Observer<DataState<List<Event>>>{}
        val latch = CountDownLatch(1)
        try {
            //REGISTRA OBSERVER
           CoroutineScope(Main).launch {
               viewModel.eventsLv.observeForever(observer)
           }
            //INVOCA EVENTO
            CoroutineScope(IO).launch{
                viewModel.callStateListener(EventStateListener.GetEvents)
            }

            //TESTA SE O LOADING FOI INVOCADO
            latch.await(100,TimeUnit.MILLISECONDS)
            var value = viewModel.eventsLv.value
            print("value : $value")
            assertThat(value,(not(nullValue())))
            assertThat(value, `is`(DataState.Loading))

            //TESTA SE O SUCESS FOI INVOCADO
            latch.await(3,TimeUnit.SECONDS)
            value = viewModel.eventsLv.value
            print("value : $value")
            assertThat(value,(not(nullValue())))
            assertTrue(value is DataState.Success)
            
        }finally {
          CoroutineScope(Main).launch {
              viewModel.eventsLv.removeObserver(observer)
          }
        }
    }

    @Test
    fun testGetEventsByIdViewModel(){
        val observer = Observer<DataState<Event>>{}
        val latch = CountDownLatch(1)
        try {
            //REGISTRA OBSERVER
            CoroutineScope(Main).launch {
                viewModel.selectedEventLv.observeForever(observer)
            }
            //INVOCA EVENTO
            CoroutineScope(IO).launch{
                viewModel.callStateListener(EventStateListener.GetEvent("1"))
            }

            //TESTA SE O LOADING FOI INVOCADO
            latch.await(100,TimeUnit.MILLISECONDS)
            var value = viewModel.selectedEventLv.value
            print("value : $value")
            assertThat(value,(not(nullValue())))
            assertThat(value, `is`(DataState.Loading))

            //TESTA SE O SUCESS FOI INVOCADO
            latch.await(3,TimeUnit.SECONDS)
            value = viewModel.selectedEventLv.value
            print("value : $value")
            assertThat(value,(not(nullValue())))
            assertTrue(value is DataState.Success)

        }finally {
            CoroutineScope(Main).launch {
                viewModel.selectedEventLv.removeObserver(observer)
            }
        }

    }

    @Test
    fun testCheckInViewModel(){
        val observer = Observer<DataState<Boolean>>{}
        val latch = CountDownLatch(1)
        try {
            //REGISTRA OBSERVER
            CoroutineScope(Main).launch {
                viewModel.checkInLv.observeForever(observer)
            }
            //INVOCA EVENTO
            CoroutineScope(IO).launch{
                viewModel.callStateListener(EventStateListener
                    .PostCheckIn(CheckIn("1","nome","test@email.com")))
            }

            //TESTA SE O LOADING FOI INVOCADO
            latch.await(100,TimeUnit.MILLISECONDS)
            var value = viewModel.checkInLv.value
            print("value : $value")
            assertThat(value,(not(nullValue())))
            assertThat(value, `is`(DataState.Loading))

            //TESTA SE O SERVER ERROR FOI INVOCADO
            latch.await(3,TimeUnit.SECONDS)
            value = viewModel.checkInLv.value
            print("value : $value")
            assertThat(value,(not(nullValue())))
            assertTrue(value is DataState.ServerError)


        }finally {
            CoroutineScope(Main).launch {
                viewModel.checkInLv.removeObserver(observer)
            }
        }
    }
}