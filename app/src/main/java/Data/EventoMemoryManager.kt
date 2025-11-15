package Data

import Entity.Evento

object EventoMemoryManager : EventoDataManager {
    private val eventList = mutableListOf<Evento>()

    override fun add(event: Evento) {
        eventList.add(event)
    }

    override fun update(event: Evento) {
        remove(event.id)
        add(event)
    }

    override fun remove(id: String) {
        eventList.removeIf { it.id.trim() == id.trim() }
    }

    override fun getAll() = eventList.toList()

    override fun getById(id: String): Evento? {
        return eventList.find { it.id.trim() == id.trim() }
    }
}