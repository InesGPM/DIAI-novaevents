package pt.unl.fct.iadi.novaevents.controller

import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import pt.unl.fct.iadi.novaevents.controller.dto.EventFormDto
import pt.unl.fct.iadi.novaevents.model.EventType
import pt.unl.fct.iadi.novaevents.service.ClubService
import pt.unl.fct.iadi.novaevents.service.EventService

@Controller
class EventController(val eventService: EventService, val clubService: ClubService) {

    @GetMapping("/events")
    fun listEvents(
        @RequestParam(required = false) type: EventType?,
        @RequestParam(required = false) clubId: Long?,
        model: Model
    ): String {
        val clubs = clubService.getAll()
        var events = eventService.getAll()

        if (type != null) events = events.filter { it.type == type }
        if (clubId != null) events = events.filter { it.clubId == clubId }

        model.addAttribute("events", events)
        model.addAttribute("clubs", clubs)
        model.addAttribute("clubMap", clubs.associate { it.id to it.name })
        return "events/list"
    }

    @GetMapping("/clubs/{clubId}/events/{id}")
    fun eventDetail(@PathVariable clubId: Long, @PathVariable id: Long, model: Model): String {
        model.addAttribute("event", eventService.getById(id))
        model.addAttribute("club", clubService.getById(clubId))
        return "events/detail"
    }

    @GetMapping("/clubs/{clubId}/events/new")
    fun showCreateForm(@PathVariable clubId: Long, model: Model): String {
        model.addAttribute("club", clubService.getById(clubId))
        model.addAttribute("form", EventFormDto())
        return "events/new"
    }

    @PostMapping("/clubs/{clubId}/events")
    fun createEvent(@PathVariable clubId: Long, @Valid @ModelAttribute("form") form: EventFormDto,
        bindingResult: BindingResult, model: Model): String {
        if (bindingResult.hasErrors()) {
            model.addAttribute("club", clubService.getById(clubId))
            return "events/new"  // volta ao formulário com erros
        }

        val event = eventService.create(
            clubId = clubId,
            name = form.name,
            date = form.date!!,
            location = form.location,
            type = form.type!!,
            description = form.description
        )
        return "redirect:/clubs/$clubId/events/${event.id}"
    }

    @GetMapping("/clubs/{clubId}/events/{id}/edit")
    fun showEditForm(@PathVariable clubId: Long, @PathVariable id: Long, model: Model): String {
        val event = eventService.getById(id)
        val form = EventFormDto(
            name = event.name,
            date = event.date,
            location = event.location,
            type = event.type,
            description = event.description
        )
        model.addAttribute("club", clubService.getById(clubId))  // objeto Club completo
        model.addAttribute("event", event)  // objeto Event completo
        model.addAttribute("form", form)
        return "events/edit"
    }

    @PostMapping("/clubs/{clubId}/events/{id}", params = ["_method=PUT"])
    fun updateEvent(@PathVariable clubId: Long, @PathVariable id: Long,
        @Valid @ModelAttribute("form") form: EventFormDto, bindingResult: BindingResult, model: Model): String {
            if (bindingResult.hasErrors()) {
                model.addAttribute("club", clubService.getById(clubId))
                return "events/edit"  // volta ao formulário com erros
            }
        val event = eventService.update(
            id= id,
            name = form.name,
            date = form.date!!,
            location = form.location,
            type = form.type!!,
            description = form.description
        )
        return "redirect:/clubs/$clubId/events/${event.id}"
    }

    @GetMapping("/clubs/{clubId}/events/{id}/delete")
    fun showDeleteConfirm(@PathVariable clubId: Long, @PathVariable id: Long, model: Model): String {
        model.addAttribute("club", clubService.getById(clubId))
        model.addAttribute("event", eventService.getById(id))
        return "events/delete"
    }

    @PostMapping("/clubs/{clubId}/events/{id}", params = ["_method=DELETE"])
    fun deleteEvent(@PathVariable clubId: Long, @PathVariable id: Long): String {
        eventService.delete(id)
        return "redirect:/clubs/$clubId"
    }

}