
package cr.ac.utn.practicaltest.controller

import cr.ac.utn.practicaltest.data.MemoryEventDataManager
import cr.ac.utn.practicaltest.model.Event

class EventController(private val dataManager: MemoryEventDataManager) {

    fun getAllEvents(): List<Event> = dataManager.getAllEvents()

    fun createEvent(title: String, description: String, date: String, location: String): Event {
        val event = Event(title = title, description = description, date = date, location = location)
        return dataManager.addEvent(event)
    }

    fun updateEvent(id: Int, title: String, description: String, date: String, location: String): Boolean {
        val event = dataManager.getEventById(id) ?: return false
        val updated = event.copy(title = title, description = description, date = date, location = location)
        return dataManager.updateEvent(updated)
    }

    fun deleteEvent(id: Int): Boolean = dataManager.deleteEvent(id)
}