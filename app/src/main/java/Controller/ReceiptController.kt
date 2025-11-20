package Controller

import Data.DataManagerReceipt.DataManagerReceipt
import Data.IDataManagerReceipt.IReceipt
import Entity.Receipt
import android.content.Context
import cr.ac.utn.practicaltest.R

class ReceiptController {
    private var context: Context
    private var dataManager: IReceipt = DataManagerReceipt

    constructor(dataManager: IReceipt, context: Context) {
        this.dataManager = dataManager
        this.context = context
    }

    constructor(context: Context) {
        this.context = context
    }

    fun addReceipt(receipt: Receipt) {
        try {
            dataManager.add(receipt)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgAdd))
        }
    }

    fun updateReceipt(receipt: Receipt) {
        try {
            dataManager.update(receipt)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgUpdate))
        }
    }

    fun getAllReceipts(): List<Receipt> {
        try {
            return dataManager.getAll()
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgGetAll))
        }
    }

    fun getReceiptById(receiptId: String): Receipt? {
        try {
            return dataManager.getById(receiptId)
        } catch (e: Exception) {
            throw Exception("Error al obtener dato específico")
        }
    }

    fun removeReceipt(receiptId: String) {
        try {
            val result = dataManager.getById(receiptId)
            if (result == null) {
                throw Exception(context.getString(R.string.MsgDataNoFound))
            }
            dataManager.remove(receiptId)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgRemove))
        }
    }
}