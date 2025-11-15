package Data

import Entity.Person
import Entity.Space
import Entity.Reservation
import java.time.LocalDateTime

interface IDataManager {
    // Person methods
    fun add (person: Person)
    fun update (person: Person)
    fun remove (id: String)
    fun getAll(): List<Person>
    fun getById(id: String): Person?
    fun getByFullName(fullName: String): Person?

    // Space methods
    fun addSpace(space: Space)
    fun updateSpace(space: Space)
    fun removeSpace(id: String)
    fun getAllSpaces(): List<Space>
    fun getSpaceById(id: String): Space?
    fun getSpacesByType(type: String): List<Space>
    fun getAvailableSpaces(): List<Space>

    // Reservation methods
    fun addReservation(reservation: Reservation)
    fun updateReservation(reservation: Reservation)
    fun removeReservation(id: String)
    fun getAllReservations(): List<Reservation>
    fun getReservationById(id: String): Reservation?
    fun getReservationsByPersonId(personId: String): List<Reservation>
    fun getReservationsBySpaceId(spaceId: String): List<Reservation>
    fun getReservationsByDateRange(startDate: LocalDateTime, endDate: LocalDateTime): List<Reservation>
    fun checkSpaceAvailability(spaceId: String, startDateTime: LocalDateTime, endDateTime: LocalDateTime): Boolean
}