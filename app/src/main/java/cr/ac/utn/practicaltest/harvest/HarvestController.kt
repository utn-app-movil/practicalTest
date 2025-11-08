package cr.ac.utn.practicaltest.harvest

class HarvestController(private val dm: HarvestDataManager) {
    fun add(name: String, responsible: String, sow: String, harvest: String) =
        dm.add(HarvestEntity(0, name, responsible, sow, harvest))
    fun update(id: Int, name: String, responsible: String, sow: String, harvest: String) =
        dm.update(HarvestEntity(id, name, responsible, sow, harvest))
    fun delete(id: Int) = dm.delete(id)
    fun all(): List<HarvestEntity> = dm.getAll()
}