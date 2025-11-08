package cr.ac.utn.practicaltest.harvest

interface HarvestDataManager {
    fun add(entity: HarvestEntity)
    fun update(entity: HarvestEntity)
    fun delete(id: Int)
    fun getAll(): List<HarvestEntity>
}