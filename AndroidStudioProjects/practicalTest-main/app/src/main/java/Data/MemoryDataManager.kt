package Data

import Entity.Person
import Entity.Space
import Entity.Reservation
import java.time.LocalDateTime

object MemoryDataManager: IDataManager {
    private var personList = mutableListOf<Person>()
    private var spaceList = mutableListOf<Space>()
    private var reservationList = mutableListOf<Reservation>()

    // Person methods
    override fun add(person: Person) {
        personList.add(person)
    }

    override fun remove(id: String) {
        personList.removeIf { it.ID.trim() == id.trim() }
    }

    override fun update(person: Person) {
        remove(person.ID)
        add(person)
    }

    override fun getAll()= personList

    override fun getById(id: String): Person? {
        val result = personList.filter { it.ID.trim() == id.trim()}
        return if(result.any()) result[0] else null
    }

    override fun getByFullName(fullName: String): Person? {
        val result = personList.filter { it.FullName() == fullName.trim()}
        return if(result.any()) result[0] else null
    }

    // Space methods
    override fun addSpace(space: Space) {
        spaceList.add(space)
    }

    override fun updateSpace(space: Space) {
        removeSpace(space.ID)
        addSpace(space)
    }

    override fun removeSpace(id: String) {
        spaceList.removeIf { it.ID.trim() == id.trim() }
    }

    override fun getAllSpaces(): List<Space> = spaceList

    override fun getSpaceById(id: String): Space? {
        val result = spaceList.filter { it.ID.trim() == id.trim() }
        return if(result.any()) result[0] else null
    }

    override fun getSpacesByType(type: String): List<Space> {
        return spaceList.filter { it.Type.name.equals(type, ignoreCase = true) }
    }

    override fun getAvailableSpaces(): List<Space> {
        return spaceList.filter { it.Available }
    }

    // Reservation methods
    override fun addReservation(reservation: Reservation) {
        reservationList.add(reservation)
    }

    override fun updateReservation(reservation: Reservation) {
        removeReservation(reservation.ID)
        addReservation(reservation)
    }

    override fun removeReservation(id: String) {
        reservationList.removeIf { it.ID.trim() == id.trim() }
    }

    override fun getAllReservations(): List<Reservation> = reservationList

    override fun getReservationById(id: String): Reservation? {
        val result = reservationList.filter { it.ID.trim() == id.trim() }
        return if(result.any()) result[0] else null
    }

    override fun getReservationsByPersonId(personId: String): List<Reservation> {
        return reservationList.filter { it.PersonId.trim() == personId.trim() }
    }

    override fun getReservationsBySpaceId(spaceId: String): List<Reservation> {
        return reservationList.filter { it.SpaceId.trim() == spaceId.trim() }
    }

    override fun getReservationsByDateRange(startDate: LocalDateTime, endDate: LocalDateTime): List<Reservation> {
        return reservationList.filter {
            it.StartDateTime >= startDate && it.EndDateTime <= endDate
        }
    }

    override fun checkSpaceAvailability(spaceId: String, startDateTime: LocalDateTime, endDateTime: LocalDateTime): Boolean {
        val conflictingReservations = reservationList.filter {
            it.SpaceId.trim() == spaceId.trim() &&
            it.Status != Entity.ReservationStatus.CANCELADA &&
            (
                (startDateTime >= it.StartDateTime && startDateTime < it.EndDateTime) ||
                (endDateTime > it.StartDateTime && endDateTime <= it.EndDateTime) ||
                (startDateTime <= it.StartDateTime && endDateTime >= it.EndDateTime)
            )
        }
        return conflictingReservations.isEmpty()
    }
}