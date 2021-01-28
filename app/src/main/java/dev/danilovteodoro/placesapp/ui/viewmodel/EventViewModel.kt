package dev.danilovteodoro.placesapp.ui.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import dev.danilovteodoro.placesapp.model.Event
import dev.danilovteodoro.placesapp.repository.EventRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import util.DataState

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

    fun callStateListener(stateListener: EventStateListener){
        when(stateListener){
            is EventStateListener.GetEvents -> {
                getEvents()
            }
            is EventStateListener.GetEvent -> {
                getEvent(stateListener.eventId)
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

}

sealed class EventStateListener{
    object GetEvents : EventStateListener()
    data class GetEvent(val eventId:String):EventStateListener()
}