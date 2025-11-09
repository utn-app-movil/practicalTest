package cr.ac.utn.practicaltest

import java.io.Serializable

data class Recipe(
    val id: Long = System.currentTimeMillis(),
    var name: String,
    var ingredients: String,
    var steps: String,
    var preparationTime: Int,
    var servings: Int
) : Serializable