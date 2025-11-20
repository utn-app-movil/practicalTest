package Data.DataManagerReceipt

import Data.IDataManagerReceipt.IReceipt
import Entity.Receipt

object DataManagerReceipt: IReceipt {
    private var receiptList = mutableListOf<Receipt>()
    override fun add(receipt: Receipt) {
        receiptList.add(receipt)
    }

    override fun update(receipt: Receipt) {
        remove(receipt.ReceiptId)
        add(receipt)
    }

    override fun remove(receipt_id: String) {
        receiptList.removeIf { it.ReceiptId.trim() == receipt_id.trim() }
    }

    override fun getById(receipt_id: String): Receipt? {
        try {
            var result = receiptList.filter { it.ReceiptId == receipt_id }
            return if (result.any()) result[0] else null
        }catch (e: Exception){
            throw e
        }
    }

    override fun getAll(): List<Receipt> = receiptList

    override fun getByEmitter(emitter_name: String): Receipt? {
        try {
            var result = receiptList.filter { it.EmitterName == emitter_name }
            return if (result.any()) result[0] else null
        }catch (e: Exception){
            throw e
        }
    }

}