package Data

import Entity.Receipt

interface IDataManagerReceipt {


    fun add (receipt: Receipt)
    fun update (receipt: Receipt)
    fun remove (id: String)
    fun getAll(): MutableList<Receipt>
    fun getById(id: String): Receipt?
}