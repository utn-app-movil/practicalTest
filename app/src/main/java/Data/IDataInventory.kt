package Data

import Entity.Inventory

interface IDataInventory {

    fun add (inventory: Inventory)
    fun update (inventory: Inventory)
    fun remove (id: String)
    fun getAll(): List<Inventory>
    fun getById(id: String): Inventory?
}