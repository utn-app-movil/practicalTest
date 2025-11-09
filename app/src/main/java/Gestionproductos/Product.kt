package cr.ac.utn.practicaltest.Gestionproductos

data class Product(
    var id: Int = 0,
    var name: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var stock: Int = 0
)
