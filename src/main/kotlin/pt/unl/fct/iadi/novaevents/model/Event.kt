package pt.unl.fct.iadi.novaevents.model

import java.time.LocalDate

data class Event(
    val id: Long,
    val clubId: Long,
    val name: String,
    val date: LocalDate,
    val location: String?,
    val type: EventType,
    val description: String?,
)