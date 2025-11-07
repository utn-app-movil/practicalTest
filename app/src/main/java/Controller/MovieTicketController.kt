package Controller

import Data.IDataManager
import Data.MemoryDataManager
import Entity.MovieTicket
import android.content.Context
import cr.ac.utn.practicaltest.R

class `MovieTicketController` {
    private var dataManager: IDataManager = MemoryDataManager
    private var context: Context

    constructor(context: Context) {
        this.context = context
    }

    fun addTicket(ticket: MovieTicket) {
        try {
            dataManager.add(ticket)
        } catch (e: Exception) {
            throw Exception(context
                .getString(R.string.ErrorMsgAdd))
        }
    }

    fun updateTicket(ticket: MovieTicket) {
        try {
            dataManager.update(ticket)
        } catch (e: Exception) {
            throw Exception(context
                .getString(R.string.ErrorMsgUpdate))
        }
    }

    fun getById(id: String): MovieTicket? {
        try {
            return dataManager.getById(id)
        } catch (e: Exception) {
            throw Exception(context
                .getString(R.string.ErrorMsgGetById))
        }
    }

    fun removeTicket(id: String) {
        try {
            val result = dataManager.getById(id)
            if (result == null) {
                throw Exception(context
                    .getString(R.string.MsgDataNoFound))
            }
            dataManager.remove(id)
        } catch (e: Exception) {
            throw Exception(context
                .getString(R.string.ErrorMsgRemove))
        }
    }
}