package Data

import Entity.Product

object ProductMemoryDataManager : IProductDataManager {
    private var productList = mutableListOf<Product>()

    override fun add(product: Product) {
        productList.add(product)
    }

    override fun update(product: Product) {
        remove(product.ID)
        add(product)
    }

    override fun remove(id: String) {
        productList.removeIf { it.ID.trim() == id.trim() }
    }

    override fun getAll(): List<Product> = productList

    override fun getById(id: String): Product? {
        val result = productList.filter { it.ID.trim() == id.trim() }
        return if (result.any()) result[0] else null
    }

    override fun getByName(name: String): Product? {
        val result = productList.filter { it.Name.equals(name.trim(), ignoreCase = true) }
        return if (result.any()) result[0] else null
    }
}
