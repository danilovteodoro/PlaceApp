package dev.danilovteodoro.placesapp.network.mapper

import dev.danilovteodoro.placesapp.model.CheckIn
import dev.danilovteodoro.placesapp.network.CheckInNetwork
import util.EntityMapper
import javax.inject.Inject

class CheckInMapper
@Inject constructor() : EntityMapper<CheckInNetwork,CheckIn>{

    override fun mapFromEntity(e: CheckInNetwork): CheckIn {
        return CheckIn(
            e.eventId,
            e.name,
            e.email
        )
    }

    override fun mapToEntity(d: CheckIn): CheckInNetwork {
        return CheckInNetwork(
            d.eventId,
            d.name,
            d.email
        )
    }
}