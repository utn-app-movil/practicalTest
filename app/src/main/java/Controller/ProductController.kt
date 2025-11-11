package Controller

import Data.IProductDataManager
import Data.ProductMemoryDataManager
import Entity.Product
import android.content.Context
import cr.ac.utn.practicaltest.R

class ProductController(context: Context) {
    private var dataManager: IProductDataManager = ProductMemoryDataManager
    private var context: Context = context

    fun add(product: Product) {
        try {
            // Validaciones básicas
            if (product.ID.isBlank() || product.Name.isBlank())
                throw Exception(context.getString(R.string.MsgDataNoFound))

            // Evitar duplicados por ID
            if (dataManager.getById(product.ID) != null)
                throw Exception(context.getString(R.string.MsgDuplicateDate))

            dataManager.add(product)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgAdd))
        }
    }

    fun update(product: Product) {
        try {
            if (product.ID.isBlank())
                throw Exception(context.getString(R.string.MsgDataNoFound))
            if (dataManager.getById(product.ID) == null)
                throw Exception(context.getString(R.string.MsgDataNoFound))

            dataManager.update(product)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgUpdate))
        }
    }

    fun remove(id: String) {
        try {
            val found = dataManager.getById(id)
            if (found == null)
                throw Exception(context.getString(R.string.MsgDataNoFound))

            dataManager.remove(id)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgRemove))
        }
    }

    fun getAll(): List<Product> {
        try {
            return dataManager.getAll()
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgGetAll))
        }
    }

    fun getById(id: String): Product? {
        try {
            return dataManager.getById(id)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgGetById))
        }
    }
}
