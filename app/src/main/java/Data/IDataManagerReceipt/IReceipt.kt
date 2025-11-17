package Data.IDataManagerReceipt

import Entity.Receipt


interface IReceipt {
    fun add(receipt: Receipt)
    fun update(receipt: Receipt)
    fun remove(receipt_id: String)
    fun getById(receipt_id: String): Receipt?
    fun getAll(): List<Receipt>
    fun getByEmitter(emitter_name: String): Receipt?
}