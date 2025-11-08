package Controller

import Data.IDataManager
import Data.MemoryDataManager
import Entity.Reservation
import Entity.ReservationStatus
import android.content.Context
import cr.ac.utn.practicaltest.R
import java.time.LocalDateTime

class ReservationController {
    private var dataManager: IDataManager = MemoryDataManager
    private var context: Context

    constructor(context: Context){
        this.context = context
    }

    fun addReservation(reservation: Reservation){
        try {
            // Validaciones básicas
            if (reservation.SpaceId.trim().isEmpty()) {
                throw Exception("Debe seleccionar un espacio")
            }
            if (reservation.PersonId.trim().isEmpty()) {
                throw Exception("Debe seleccionar una persona")
            }
            if (reservation.Purpose.trim().isEmpty()) {
                throw Exception("Debe especificar el propósito de la reserva")
            }
            if (reservation.NumberOfAttendees <= 0) {
                throw Exception("El número de asistentes debe ser mayor a 0")
            }

            // Validar fechas
            if (reservation.StartDateTime.isAfter(reservation.EndDateTime)) {
                throw Exception("La fecha de inicio debe ser anterior a la fecha de fin")
            }
            if (reservation.StartDateTime.isBefore(LocalDateTime.now())) {
                throw Exception("La fecha de inicio no puede ser en el pasado")
            }

            // Verificar que el espacio existe
            val space = dataManager.getSpaceById(reservation.SpaceId)
            if (space == null) {
                throw Exception("El espacio seleccionado no existe")
            }

            // Verificar que el espacio está disponible
            if (!space.Available) {
                throw Exception("El espacio seleccionado no está disponible")
            }

            // Verificar capacidad
            if (reservation.NumberOfAttendees > space.Capacity) {
                throw Exception("El número de asistentes (${reservation.NumberOfAttendees}) excede la capacidad del espacio (${space.Capacity})")
            }

            // Verificar disponibilidad en el horario solicitado
            val isAvailable = dataManager.checkSpaceAvailability(
                reservation.SpaceId,
                reservation.StartDateTime,
                reservation.EndDateTime
            )
            if (!isAvailable) {
                throw Exception("El espacio no está disponible en el horario solicitado")
            }

            // Calcular precio total
            val hours = reservation.getDurationInHours()
            reservation.TotalPrice = hours * space.PricePerHour

            dataManager.addReservation(reservation)
        } catch (e: Exception){
            throw Exception(context.getString(R.string.ErrorMsgAdd) + ": ${e.message}")
        }
    }

    fun updateReservation(reservation: Reservation){
        try {
            // Validaciones básicas
            if (reservation.Purpose.trim().isEmpty()) {
                throw Exception("Debe especificar el propósito de la reserva")
            }
            if (reservation.NumberOfAttendees <= 0) {
                throw Exception("El número de asistentes debe ser mayor a 0")
            }

            val existing = dataManager.getReservationById(reservation.ID)
            if (existing == null) {
                throw Exception("La reserva no existe")
            }

            // Validar fechas
            if (reservation.StartDateTime.isAfter(reservation.EndDateTime)) {
                throw Exception("La fecha de inicio debe ser anterior a la fecha de fin")
            }

            // Verificar que el espacio existe
            val space = dataManager.getSpaceById(reservation.SpaceId)
            if (space == null) {
                throw Exception("El espacio seleccionado no existe")
            }

            // Verificar capacidad
            if (reservation.NumberOfAttendees > space.Capacity) {
                throw Exception("El número de asistentes excede la capacidad del espacio")
            }

            // Si cambió el horario, verificar disponibilidad
            if (reservation.StartDateTime != existing.StartDateTime ||
                reservation.EndDateTime != existing.EndDateTime ||
                reservation.SpaceId != existing.SpaceId) {

                // Temporalmente remover la reserva actual para verificar disponibilidad
                dataManager.removeReservation(reservation.ID)
                val isAvailable = dataManager.checkSpaceAvailability(
                    reservation.SpaceId,
                    reservation.StartDateTime,
                    reservation.EndDateTime
                )

                if (!isAvailable) {
                    // Restaurar la reserva original si no hay disponibilidad
                    dataManager.addReservation(existing)
                    throw Exception("El espacio no está disponible en el nuevo horario")
                }

                // Recalcular precio
                val hours = reservation.getDurationInHours()
                reservation.TotalPrice = hours * space.PricePerHour
            }

            dataManager.updateReservation(reservation)
        } catch (e: Exception){
            throw Exception(context.getString(R.string.ErrorMsgUpdate) + ": ${e.message}")
        }
    }

    fun getReservationById(id: String): Reservation? {
        try {
            return dataManager.getReservationById(id)
        } catch (e: Exception){
            throw Exception(context.getString(R.string.ErrorMsgGetById) + ": ${e.message}")
        }
    }

    fun getAllReservations(): List<Reservation> {
        try {
            return dataManager.getAllReservations()
        } catch (e: Exception){
            throw Exception("Error al obtener las reservas: ${e.message}")
        }
    }

    fun getReservationsByPersonId(personId: String): List<Reservation> {
        try {
            return dataManager.getReservationsByPersonId(personId)
        } catch (e: Exception){
            throw Exception("Error al obtener las reservas de la persona: ${e.message}")
        }
    }

    fun getReservationsBySpaceId(spaceId: String): List<Reservation> {
        try {
            return dataManager.getReservationsBySpaceId(spaceId)
        } catch (e: Exception){
            throw Exception("Error al obtener las reservas del espacio: ${e.message}")
        }
    }

    fun getReservationsByDateRange(startDate: LocalDateTime, endDate: LocalDateTime): List<Reservation> {
        try {
            return dataManager.getReservationsByDateRange(startDate, endDate)
        } catch (e: Exception){
            throw Exception("Error al obtener las reservas por fecha: ${e.message}")
        }
    }

    fun removeReservation(id: String){
        try {
            val result = dataManager.getReservationById(id)
            if (result == null){
                throw Exception(context.getString(R.string.MsgDataNoFound))
            }
            dataManager.removeReservation(id)
        } catch (e: Exception){
            throw Exception(context.getString(R.string.ErrorMsgRemove) + ": ${e.message}")
        }
    }

    fun cancelReservation(id: String){
        try {
            val reservation = dataManager.getReservationById(id)
            if (reservation == null){
                throw Exception(context.getString(R.string.MsgDataNoFound))
            }

            if (reservation.Status == ReservationStatus.CANCELADA) {
                throw Exception("La reserva ya está cancelada")
            }
            if (reservation.Status == ReservationStatus.COMPLETADA) {
                throw Exception("No se puede cancelar una reserva completada")
            }

            reservation.Status = ReservationStatus.CANCELADA
            dataManager.updateReservation(reservation)
        } catch (e: Exception){
            throw Exception("Error al cancelar la reserva: ${e.message}")
        }
    }

    fun confirmReservation(id: String){
        try {
            val reservation = dataManager.getReservationById(id)
            if (reservation == null){
                throw Exception(context.getString(R.string.MsgDataNoFound))
            }

            if (reservation.Status == ReservationStatus.CANCELADA) {
                throw Exception("No se puede confirmar una reserva cancelada")
            }
            if (reservation.Status == ReservationStatus.COMPLETADA) {
                throw Exception("La reserva ya está completada")
            }

            reservation.Status = ReservationStatus.CONFIRMADA
            dataManager.updateReservation(reservation)
        } catch (e: Exception){
            throw Exception("Error al confirmar la reserva: ${e.message}")
        }
    }

    fun completeReservation(id: String){
        try {
            val reservation = dataManager.getReservationById(id)
            if (reservation == null){
                throw Exception(context.getString(R.string.MsgDataNoFound))
            }

            if (reservation.Status == ReservationStatus.CANCELADA) {
                throw Exception("No se puede completar una reserva cancelada")
            }

            reservation.Status = ReservationStatus.COMPLETADA
            dataManager.updateReservation(reservation)
        } catch (e: Exception){
            throw Exception("Error al completar la reserva: ${e.message}")
        }
    }

    fun checkSpaceAvailability(spaceId: String, startDateTime: LocalDateTime, endDateTime: LocalDateTime): Boolean {
        try {
            return dataManager.checkSpaceAvailability(spaceId, startDateTime, endDateTime)
        } catch (e: Exception){
            throw Exception("Error al verificar disponibilidad: ${e.message}")
        }
    }
}
