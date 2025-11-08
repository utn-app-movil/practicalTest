package Entity

class Recipe(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var prepTime: Int = 0, // minutos
    var cookTime: Int = 0, // minutos
    var difficulty: String = "Fácil",
    var servings: Int = 1,
    var category: String = "",
    var steps: String = "",
    var ingredients: MutableList<Ingredient> = mutableListOf()
) {
    // Lista importante




    // Constructor vacío
    constructor() : this("", "", "", 0, 0, "", 1, "", "")





    fun getTotalTime(): Int = prepTime + cookTime


}