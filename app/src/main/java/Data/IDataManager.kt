package Data

import Entity.Event

interface IDataManager {
    fun add(event: Event)
    fun update(event: Event)
    fun remove(id: String)
    fun getAll(): List<Event>
    fun getById(id: String): Event?
}