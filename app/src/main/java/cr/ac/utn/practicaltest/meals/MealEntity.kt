package cr.ac.utn.practicaltest.meals

data class MealEntity(
    val id: Int,
    var name: String,
    var quantity: Int,
    var deliveryDate: String, // dd/MM/yyyy
    var notes: String?
)
