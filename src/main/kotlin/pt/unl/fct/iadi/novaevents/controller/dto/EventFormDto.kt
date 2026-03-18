package pt.unl.fct.iadi.novaevents.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.format.annotation.DateTimeFormat
import pt.unl.fct.iadi.novaevents.model.EventType
import java.time.LocalDate

data class EventFormDto(
    @field:NotBlank(message = "Name is required")
    val name: String = "",

    @field:NotNull(message = "Date is required")
    @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val date: LocalDate? = null,

    @field:NotNull(message = "Event type is required")
    val type: EventType? = null,

    val location: String? = null,
    val description: String? = null
)