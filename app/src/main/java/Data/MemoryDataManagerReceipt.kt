package Data

import Entity.Receipt

object MemoryDataManagerReceipt: IDataManagerReceipt {
    private  var ReceiptR = mutableListOf<Receipt>()
    override fun add(receipt: Receipt) {
        ReceiptR.add(receipt)
    }

    override fun remove(id: String) {
        ReceiptR.removeIf { it.ID.trim() == id.trim() }
    }

    override fun update(receipt: Receipt) {
        remove(receipt.ID)
        add(receipt)
    }

    override fun getAll()= ReceiptR

    override fun getById(id: String): Receipt? {
        val result = ReceiptR.
        filter { it.ID.trim() == id.trim()}
        return if(result.any()) result[0] else null
    }
}