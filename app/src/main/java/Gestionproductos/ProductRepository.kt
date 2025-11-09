package cr.ac.utn.practicaltest.Gestionproductos

class ProductRepository {

    // Lista temporal para almacenar los productos
    private val productList = mutableListOf<Product>()
    private var autoIncrementId = 1

    // Crear producto
    fun addProduct(product: Product): Boolean {
        // Validar datos duplicados por nombre
        if (productList.any { it.name.equals(product.name, ignoreCase = true) }) {
            return false
        }

        product.id = autoIncrementId++
        productList.add(product)
        return true
    }

    // Obtener todos los productos
    fun getAllProducts(): List<Product> {
        return productList
    }

    // Obtener producto por ID
    fun getProductById(id: Int): Product? {
        return productList.find { it.id == id }
    }

    // Actualizar producto
    fun updateProduct(updatedProduct: Product): Boolean {
        val index = productList.indexOfFirst { it.id == updatedProduct.id }

        if (index != -1) {
            productList[index] = updatedProduct
            return true
        }
        return false
    }

    // Eliminar producto q hice
    fun deleteProduct(id: Int): Boolean {
        val product = productList.find { it.id == id }
        return if (product != null) {
            productList.remove(product)
            true
        } else {
            false
        }
    }
}
