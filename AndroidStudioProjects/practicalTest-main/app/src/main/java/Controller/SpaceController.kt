package Controller

import Data.IDataManager
import Data.MemoryDataManager
import Entity.Space
import android.content.Context
import cr.ac.utn.practicaltest.R

class SpaceController {
    private var dataManager: IDataManager = MemoryDataManager
    private var context: Context

    constructor(context: Context){
        this.context = context
    }

    fun addSpace(space: Space){
        try {
            if (space.Name.trim().isEmpty()) {
                throw Exception("El nombre del espacio no puede estar vacío")
            }
            if (space.Capacity <= 0) {
                throw Exception("La capacidad debe ser mayor a 0")
            }
            dataManager.addSpace(space)
        } catch (e: Exception){
            throw Exception(context.getString(R.string.ErrorMsgAdd) + ": ${e.message}")
        }
    }

    fun updateSpace(space: Space){
        try {
            if (space.Name.trim().isEmpty()) {
                throw Exception("El nombre del espacio no puede estar vacío")
            }
            if (space.Capacity <= 0) {
                throw Exception("La capacidad debe ser mayor a 0")
            }
            val existing = dataManager.getSpaceById(space.ID)
            if (existing == null) {
                throw Exception("El espacio no existe")
            }
            dataManager.updateSpace(space)
        } catch (e: Exception){
            throw Exception(context.getString(R.string.ErrorMsgUpdate) + ": ${e.message}")
        }
    }

    fun getSpaceById(id: String): Space? {
        try {
            return dataManager.getSpaceById(id)
        } catch (e: Exception){
            throw Exception(context.getString(R.string.ErrorMsgGetById) + ": ${e.message}")
        }
    }

    fun getAllSpaces(): List<Space> {
        try {
            return dataManager.getAllSpaces()
        } catch (e: Exception){
            throw Exception("Error al obtener los espacios: ${e.message}")
        }
    }

    fun getSpacesByType(type: String): List<Space> {
        try {
            return dataManager.getSpacesByType(type)
        } catch (e: Exception){
            throw Exception("Error al obtener los espacios por tipo: ${e.message}")
        }
    }

    fun getAvailableSpaces(): List<Space> {
        try {
            return dataManager.getAvailableSpaces()
        } catch (e: Exception){
            throw Exception("Error al obtener los espacios disponibles: ${e.message}")
        }
    }

    fun removeSpace(id: String){
        try {
            val result = dataManager.getSpaceById(id)
            if (result == null){
                throw Exception(context.getString(R.string.MsgDataNoFound))
            }
            // Verificar si hay reservas activas para este espacio
            val reservations = dataManager.getReservationsBySpaceId(id)
            val activeReservations = reservations.filter {
                it.Status != Entity.ReservationStatus.CANCELADA &&
                it.Status != Entity.ReservationStatus.COMPLETADA
            }
            if (activeReservations.isNotEmpty()) {
                throw Exception("No se puede eliminar el espacio porque tiene reservas activas")
            }
            dataManager.removeSpace(id)
        } catch (e: Exception){
            throw Exception(context.getString(R.string.ErrorMsgRemove) + ": ${e.message}")
        }
    }

    fun toggleSpaceAvailability(id: String){
        try {
            val space = dataManager.getSpaceById(id)
            if (space == null){
                throw Exception(context.getString(R.string.MsgDataNoFound))
            }
            space.Available = !space.Available
            dataManager.updateSpace(space)
        } catch (e: Exception){
            throw Exception("Error al cambiar la disponibilidad: ${e.message}")
        }
    }
}
