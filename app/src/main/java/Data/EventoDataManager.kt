package Data

import Entity.Evento

interface EventoDataManager {
    fun add(event: Evento)
    fun update(event: Evento)
    fun remove(id: String)
    fun getAll(): List<Evento>
    fun getById(id: String): Evento?
}