package Controller

import Data.IDataManager
import Data.IDataRecipe
import Data.MemoryDataManager
import Data.MemoryDataRecipe
import Entity.Person
import Entity.Recipe
import android.content.Context

class RecipeController {
    private var dataManager: IDataRecipe = MemoryDataRecipe
        private  var context: Context
    constructor(context: Context ){
        this.context = context
    }

    fun addRecipe(recipe: Recipe){
        try {
            dataManager.add(recipe)
        }catch (e: Exception){
            throw Exception("Error añadiendo")
        }
    }

    fun updateRecipe(recipe: Recipe){
        try {
            dataManager.update(recipe)
        }catch (e: Exception){
            throw Exception("Error actualizando")
        }
    }

    fun getById(id: String):Recipe?{

        try {

            return dataManager.getById(id)
        } catch (e: Exception){
            throw e
        }


    }

    fun removeRecipe(id: String){
        try {
            val result = dataManager.getById(id)
            if (result === null){
                throw Exception("No se encontró")
            }
            dataManager.remove(id)
        }catch (e: Exception){
            throw Exception("Error al borrar")
        }
    }



}