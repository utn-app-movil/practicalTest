package Controller

import Data.IDataInventory
import Data.MemoryDataInventory
import Entity.Inventory
import android.content.Context
import cr.ac.utn.practicaltest.R

class InventoryController {

    private var dataManager: IDataInventory = MemoryDataInventory
    private  var context: Context

    constructor(context: Context){
        this.context=context
    }

    fun addInventory(inventory: Inventory){
        try {
            dataManager.add(inventory)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgAdd))
        }
    }

    fun updateInventory(inventory: Inventory){
        try {
            dataManager.update(inventory)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgUpdate))
        }
    }

    fun getById(id: String): Inventory?{
        try {
            return dataManager.getById(id)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgGetById))
        }
    }

    fun removeInventory(id: String){
        try{
            val result = dataManager.getById(id)
            if (result == null){
                throw Exception(context
                    .getString(R.string.MsgDataNoFound))
            }
            dataManager.remove(id)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgRemove))
        }
    }
}