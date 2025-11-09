package Data

import Entity.Person
import Entity.Recipe

interface IDataRecipe {
    fun add (recipe: Recipe)
    fun update (recipe: Recipe)
    fun remove (id: String)
    fun getById(id: String): Recipe?

}