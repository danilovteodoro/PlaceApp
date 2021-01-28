package dev.danilovteodoro.placesapp.network.mapper

import dev.danilovteodoro.placesapp.model.Event
import dev.danilovteodoro.placesapp.network.EventNetwork
import util.EntityMapper
import javax.inject.Inject

class EventMapper @Inject constructor() :EntityMapper<EventNetwork,Event>{

    override fun mapFromEntity(e: EventNetwork): Event {
        return Event(
            people = e.people,
            date = e.date,
            description = e.description,
            image = e.image,
            latitude = e.latitude,
            longitude = e.longitude,
            price = e.price,
            title = e.title,
            id = e.id
        )
    }

    override fun mapToEntity(d: Event): EventNetwork {
        return EventNetwork(
            people = d.people,
            date =  d.date,
            description = d.description,
            image = d.image,
            latitude = d.latitude,
            longitude = d.longitude,
            price = d.price,
            title = d.title,
            id = d.id
        )
    }

    fun mapFromEntityList(entities:List<EventNetwork>):List<Event>{
        return entities.map { entity -> mapFromEntity(entity) }
    }
}