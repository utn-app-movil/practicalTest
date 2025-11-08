package Controller

import Data.IData
import Data.MemoryData
import Entity.Inscription
import android.content.Context
import cr.ac.utn.practicaltest.R

class InscriptionController {
    private var dataManager: IData = MemoryData
    private var context: Context

    constructor(context: Context){
        this.context=context
    }

    fun addInscription(inscription: Inscription){
        try {
            dataManager.addInscription(inscription)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgAdd))
        }
    }

    fun updateInscription(inscription: Inscription){
        try {
            dataManager.updateInscription(inscription)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgUpdate))
        }
    }

    fun getByIdInscription(id: String): Inscription?{
        try {
            return dataManager.getByIdInscription(id)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgGetById))
        }
    }

    fun removeInscription(id: String){
        try{
            val result = dataManager.getByIdInscription(id)
            if (result == null){
                throw Exception(context
                    .getString(R.string.MsgDataNoFound))
            }
            dataManager.removeInscription(id)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgRemove))
        }
    }
}
