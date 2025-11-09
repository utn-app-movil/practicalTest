package Controller

import Data.IDataManagerReceipt
import Data.MemoryDataManagerReceipt
import Entity.Receipt
import android.content.Context
import cr.ac.utn.practicaltest.R

class ReceiptController {

    private var dataManager: IDataManagerReceipt = MemoryDataManagerReceipt
    private  var context: Context

    constructor(context: Context){
        this.context=context
    }

    fun addReceipt(receipt: Receipt){
        try {
            dataManager.add(receipt)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgAdd))
        }
    }

    fun updateReceipt(receipt: Receipt){
        try {
            dataManager.update(receipt)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgUpdate))
        }
    }

    fun getById(id: String): Receipt?{
        try {
            return dataManager.getById(id)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgGetById))
        }
    }

    fun removeReceipt(id: String){
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
