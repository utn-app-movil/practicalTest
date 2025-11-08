package Controller

import Data.SpDataManager
import Data.SpMemoryDataManager
import Entity.Supplier
import android.content.Context
import cr.ac.utn.practicaltest.R

class SupplierController {

    private var dataManager: SpDataManager = SpMemoryDataManager

    private var context: Context

        constructor(context: Context){
        this.context=context
    }

    fun addSupplier(supplier: Supplier){
        try {
            dataManager.add(supplier)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgAdd))
        }
    }

    fun updateSupplier(supplier: Supplier){
        try {
            dataManager.update(supplier)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgUpdate))
        }
    }

    fun getById(id: String): Supplier?{
        try {
            return dataManager.getById(id)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgGetById))
        }
    }

    fun getByName(name: String): Supplier?{
        try {
            return dataManager.
            getByName(name)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgGetById))
        }
    }

    fun removeSupplier(id: String){
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