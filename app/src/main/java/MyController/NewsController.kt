package cr.ac.utn.practicaltest.MyController

import android.content.Context
import cr.ac.utn.practicaltest.MyData.MemoryNewsManager
import cr.ac.utn.practicaltest.MyEntity.News

class NewsController(private val context: Context) {
    fun add(item: News) = MemoryNewsManager.add(item)
    fun update(item: News) = MemoryNewsManager.update(item)
    fun remove(id: String) = MemoryNewsManager.remove(id)
    fun getById(id: String) = MemoryNewsManager.getById(id)
    fun getAll() = MemoryNewsManager.getAll()
}