package dev.danilovteodoro.placesapp.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ActivityContext
import dev.danilovteodoro.placesapp.model.Event
import dev.danilovteodoro.placesapp.network.api.EventApi
import dev.danilovteodoro.placesapp.network.mapper.EventMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import util.AndroidUtil
import util.DataState
import javax.inject.Inject

class EventRepository
@Inject
constructor(
    val eventApi: EventApi,
    val eventMapper: EventMapper,
    @ActivityContext val context:Context
){
    fun getEvents():Flow<DataState<List<Event>>> = flow {
        emit(DataState.Loading)
        if (!AndroidUtil.isNetworkConnected(context)){
            emit(DataState.NoConnection)
            return@flow
        }
        val eventsNetwork = eventApi.getEvents()
        val events:List<Event> = eventMapper.mapFromEntityList(eventsNetwork)
        emit(DataState.Success(events))
    }.catch { e->
        emit(DataState.dataStateFromError(e))
    }

    fun getEventById(id:String):Flow<DataState<Event>> = flow {
        emit(DataState.Loading)
        if (!AndroidUtil.isNetworkConnected(context)) {
            emit(DataState.NoConnection)
            return@flow
        }
        val eventNetwork = eventApi.getEventById(id)
        val event: Event = eventMapper.mapFromEntity(eventNetwork)
        emit(DataState.Success(event))
    }.catch { e->
        emit(DataState.dataStateFromError(e))
    }
}