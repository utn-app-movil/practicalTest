
package cr.ac.utn.practicaltest.data

import cr.ac.utn.practicaltest.model.Event

class MemoryEventDataManager {
    private val events = mutableListOf<Event>()
    private var nextId = 1

    fun getAllEvents(): List<Event> = events.toList()

    fun addEvent(event: Event): Event {
        event.id = nextId++
        events.add(event)
        return event
    }

    fun updateEvent(updatedEvent: Event): Boolean {
        val index = events.indexOfFirst { it.id == updatedEvent.id }
        return if (index != -1) {
            events[index] = updatedEvent
            true
        } else false
    }

    fun deleteEvent(id: Int): Boolean {
        return events.removeIf { it.id == id }
    }

    fun getEventById(id: Int): Event? {
        return events.find { it.id == id }
    }
}