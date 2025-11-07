package Controller

import Data.IDataManager
import Data.MemoryDataManager
import Entity.Event
import android.content.Context
import cr.ac.utn.practicaltest.R

class EventController(context: Context) {
    private val dataManager: IDataManager = MemoryDataManager
    private val context: Context = context

    fun addEvent(event: Event) {
        try {
            dataManager.add(event)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgAdd))
        }
    }

    fun updateEvent(event: Event) {
        try {
            dataManager.update(event)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgUpdate))
        }
    }

    fun deleteEvent(id: String) {
        try {
            val event = dataManager.getById(id)
            if (event == null) {
                throw Exception(context.getString(R.string.MsgDataNoFound))
            }
            dataManager.remove(id)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgRemove))
        }
    }

    fun getEventById(id: String): Event? {
        return try {
            dataManager.getById(id)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgGetById))
        }
    }

    fun getAllEvents(): List<Event> {
        return try {
            dataManager.getAll()
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgGetAll))
        }
    }
}