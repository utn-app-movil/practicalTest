package Data

import Entity.Supplier

object SpMemoryDataManager: SpDataManager {

    private  var supplierList =mutableListOf<Supplier>()

    override fun add(supplier: Supplier) {
        supplierList.add(supplier)
    }

    override fun remove(id: String) {
        supplierList.removeIf { it.ID.trim() == id.trim() }
    }

    override fun update(supplier: Supplier) {
        remove(supplier.ID)
        add(supplier)
    }

    override fun getAll()= supplierList

    override fun getById(id: String): Supplier? {
        val result = supplierList.filter { it.ID.trim() == id.trim()}
        return if(result.any()) result[0] else null
    }

    override fun getByName(name: String): Supplier? {
        val result = supplierList.
        filter { it.Name.trim() == name.trim()}
        return if(result.any()) result[0] else null
    }
}