package dev.danilovteodoro.placesapp.data.mapper

import dev.danilovteodoro.placesapp.data.EventData
import dev.danilovteodoro.placesapp.model.Event
import util.EntityMapper
import javax.inject.Inject

class EventDataMapper @Inject constructor() :EntityMapper<EventData,Event> {

    override fun mapFromEntity(e: EventData): Event {
        return Event(
            people = emptyList(),
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

    override fun mapToEntity(d: Event): EventData {
        return EventData(
            id = d.id,
            date = d.date,
            description = d.description,
            image = d.image,
            latitude = d.latitude,
            longitude = d.longitude,
            price = d.price,
            title = d.title
        )
    }

    fun fromEntityList(entities:List<EventData>):List<Event>{
        return entities.map { e-> mapFromEntity(e) }
    }


}