package dev.danilovteodoro.placesapp.ui.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import dev.danilovteodoro.placesapp.model.CheckIn
import dev.danilovteodoro.placesapp.model.Event
import dev.danilovteodoro.placesapp.repository.EventRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import util.DataState
import java.util.*

class EventViewModel
@ViewModelInject
constructor(
    val repository: EventRepository,
    @Assisted val savedStateHandle: SavedStateHandle
):ViewModel(){

    private var _eventsLv:MutableLiveData<DataState<List<Event>>> = MutableLiveData()
    val eventsLv:LiveData<DataState<List<Event>>> get() = _eventsLv

    private var _selectedEventLv:MutableLiveData<DataState<Event>> = MutableLiveData()
    val selectedEventLv:LiveData<DataState<Event>> get() = _selectedEventLv

    private var _checkInLv:MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val checkInLv:LiveData<DataState<Boolean>> get() = _checkInLv

    fun callStateListener(stateListener: EventStateListener){
        when(stateListener){
            is EventStateListener.GetEvents -> {
                getEvents()
            }
            is EventStateListener.GetEvent -> {
                getEvent(stateListener.eventId)
            }

            is EventStateListener.PostCheckIn -> {
                postCheckIn(stateListener.checkIn)
            }
        }
    }

    private fun getEvents(){
        repository.getEvents()
            .onEach { dataState ->
                _eventsLv.value = dataState
            }.launchIn(viewModelScope)
    }
    private fun getEvent(eventId:String){
        repository.getEventById(eventId).onEach {dataState ->
            _selectedEventLv.value = dataState
        }.launchIn(viewModelScope)
    }


    private fun postCheckIn(checkIn: CheckIn){
        repository.postCheckIn(checkIn)
                .onEach { dataState ->
                     _checkInLv.value = dataState
                }.launchIn(viewModelScope)
    }

}

sealed class EventStateListener{
    object GetEvents : EventStateListener()
    data class GetEvent(val eventId:String):EventStateListener()
    data class PostCheckIn(val checkIn: CheckIn):EventStateListener()
}