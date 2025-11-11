package cr.ac.utn.practicaltest.meals

interface MealDataSource {
    fun addMeal(meal: MealEntity)
    fun updateMeal(meal: MealEntity)
    fun deleteMeal(id: Int)
    fun getAllMeals(): List<MealEntity>
}
