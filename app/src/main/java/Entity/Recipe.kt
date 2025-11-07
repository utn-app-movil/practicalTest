package Entity

class Recipe(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var prepTime: Int = 0, // minutos
    var cookTime: Int = 0, // minutos
    var difficulty: String = "Fácil",
    var servings: Int = 1,
    var category: String = ""
) {
    // Listas importantes
    var ingredients: MutableList<Ingredient> = mutableListOf()
    var steps: MutableList<String> = mutableListOf()
    var tags: MutableList<String> = mutableListOf()

    // Constructor vacío
    constructor() : this("", "", "", 0, 0, "", 1, "")


    fun addIngredient(ingredient: Ingredient) {
        ingredients.add(ingredient)
    }

    fun addStep(step: String) {
        steps.add(step)
    }

    fun getTotalTime(): Int = prepTime + cookTime

    fun printRecipe() {
        println("--- $name ---")
        println("Descripción: $description")
        println("Tiempo total: ${getTotalTime()} minutos")
        println("Porciones: $servings")
        println("Dificultad: $difficulty")

        println("\n--- Ingredientes ---")
        ingredients.forEach { ingredient ->
            println("- ${ingredient.getDescription()}")
        }

        println("\n--- Pasos ---")
        steps.forEachIndexed { index, step ->
            println("${index + 1}. $step")
        }
    }
}