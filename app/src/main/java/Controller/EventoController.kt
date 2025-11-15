package Controller

import Data.EventoDataManager
import Data.EventoMemoryManager
import Entity.Evento
import android.content.Context
import cr.ac.utn.practicaltest.R

class EventoController(private val context: Context) {
    private val dataManager: EventoDataManager = EventoMemoryManager

    fun addEvent(event: Evento) {
        try { dataManager.add(event) }
        catch (e: Exception) { throw Exception(context.getString(R.string.ErrorMsgAdd)) }
    }

    fun updateEvent(event: Evento) {
        try { dataManager.update(event) }
        catch (e: Exception) { throw Exception(context.getString(R.string.ErrorMsgUpdate)) }
    }

    fun deleteEvent(id: String) {
        try {
            if (dataManager.getById(id) == null) throw Exception(context.getString(R.string.MsgDataNoFound))
            dataManager.remove(id)
        } catch (e: Exception) { throw Exception(context.getString(R.string.ErrorMsgRemove)) }
    }

    fun getEventById(id: String): Evento? {
        return try { dataManager.getById(id) }
        catch (e: Exception) { throw Exception(context.getString(R.string.ErrorMsgGetById)) }
    }

    fun getAllEvents(): List<Evento> {
        return try { dataManager.getAll() }
        catch (e: Exception) { throw Exception(context.getString(R.string.ErrorMsgGetAll)) }
    }
}