package Data

import Entity.Inventory


object MemoryDataInventory: IDataInventory {

    private  var inventoryList = mutableListOf<Inventory>()
    override fun add(inventory: Inventory) {
        inventoryList.add(inventory)
    }

    override fun remove(id: String) {
        inventoryList.removeIf { it.ID.trim() == id.trim() }
    }

    override fun update(inventory: Inventory) {
        remove(inventory.ID)
        add(inventory)
    }

    override fun getAll()= inventoryList

    override fun getById(id: String): Inventory? {
        val result = inventoryList.
        filter { it.ID.trim() == id.trim()}
        return if(result.any()) result[0] else null
    }
}