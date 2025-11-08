package cr.ac.utn.practicaltest.harvest

import java.util.concurrent.atomic.AtomicInteger

object MemoryHarvestManager : HarvestDataManager {
    private val autoinc = AtomicInteger(1)
    private val items = mutableListOf<HarvestEntity>()

    override fun add(entity: HarvestEntity) {
        items.add(entity.copy(id = autoinc.getAndIncrement()))
    }
    override fun update(entity: HarvestEntity) {
        val i = items.indexOfFirst { it.id == entity.id }
        if (i >= 0) items[i] = entity
    }
    override fun delete(id: Int) { items.removeAll { it.id == id } }
    override fun getAll(): List<HarvestEntity> = items
}