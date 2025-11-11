package Data

import Entity.Product

interface IProductDataManager {
    fun add(product: Product)
    fun update(product: Product)
    fun remove(id: String)
    fun getAll(): List<Product>
    fun getById(id: String): Product?
    fun getByName(name: String): Product?
}
