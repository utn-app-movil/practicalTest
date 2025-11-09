package cr.ac.utn.practicaltest.MyData

import cr.ac.utn.practicaltest.MyEntity.News

object MemoryNewsManager {
    private val list = mutableListOf<News>()

    fun getAll() = list.toList()
    fun add(item: News) = list.add(item)
    fun update(item: News) {
        val i = list.indexOfFirst { it.ID == item.ID }
        if (i != -1) list[i] = item
    }
    fun remove(id: String) = list.removeAll { it.ID == id }
    fun getById(id: String) = list.find { it.ID == id }
}