package Data

import Entity.Supplier

interface SpDataManager {
    fun add(supplier: Supplier)
    fun update (supplier: Supplier)
    fun remove (id: String)
    fun getAll(): List<Supplier>
    fun getById(id: String): Supplier?
    fun getByName(name: String): Supplier?
}