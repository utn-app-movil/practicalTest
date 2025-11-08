package controller

import model.Supplier

class SupplierController {

    private val suppliers = mutableListOf<Supplier>()

    fun createSupplier(supplier: Supplier) {
        suppliers.add(supplier)
    }

    fun getSupplier(id: Int): Supplier? {
        return suppliers.find { it.id == id }
    }

    fun updateSupplier(supplier: Supplier) {
        val index = suppliers.indexOfFirst { it.id == supplier.id }
        if (index != -1) {
            suppliers[index] = supplier
        }
    }

    fun deleteSupplier(id: Int) {
        suppliers.removeAll { it.id == id }
    }

    fun getAllSuppliers(): List<Supplier> {
        return suppliers
    }
}