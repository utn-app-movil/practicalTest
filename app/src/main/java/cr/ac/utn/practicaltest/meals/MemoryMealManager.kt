package cr.ac.utn.practicaltest.meals

class MemoryMealManager : MealDataSource {
    private val meals = mutableListOf<MealEntity>()
    private var autoId = 1

    override fun addMeal(meal: MealEntity) {
        meals.add(meal.copy(id = autoId++))
    }
    override fun updateMeal(meal: MealEntity) {
        val i = meals.indexOfFirst { it.id == meal.id }
        if (i != -1) meals[i] = meal
    }
    override fun deleteMeal(id: Int) { meals.removeAll { it.id == id } }
    override fun getAllMeals(): List<MealEntity> = meals
}
