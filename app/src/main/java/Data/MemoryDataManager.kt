package Data

import Entity.Event

object MemoryDataManager : IDataManager {
    private val eventList = mutableListOf<Event>()

    override fun add(event: Event) {
        eventList.add(event)
    }

    override fun update(event: Event) {
        remove(event.id)
        add(event)
    }

    override fun remove(id: String) {
        eventList.removeIf { it.id.trim() == id.trim() }
    }

    override fun getAll() = eventList.toList()

    override fun getById(id: String): Event? {
        return eventList.find { it.id.trim() == id.trim() }
    }
}