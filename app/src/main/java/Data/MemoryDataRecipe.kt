package Data


import Entity.Recipe

object MemoryDataRecipe: IDataRecipe {
    private var recipeList = mutableListOf<Recipe>()
    override fun add(recipe: Recipe) {
        recipeList.add(recipe)
    }

    override fun update(recipe: Recipe) {
        remove(recipe.id)
    }

    override fun remove(id: String) {
        recipeList.removeIf { it.id.trim() == id.trim() }
    }

    override fun getById(id: String): Recipe? {
        try {
            val result = recipeList.
            filter { it.id.trim() == id.trim() }
            return if (result.any()) result[0] else null

        }catch (e:  Exception){
            throw e
        }
    }
}