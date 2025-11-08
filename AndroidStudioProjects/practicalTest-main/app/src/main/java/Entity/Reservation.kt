package Entity

import java.time.LocalDateTime

enum class ReservationStatus {
    PENDIENTE,
    CONFIRMADA,
    CANCELADA,
    COMPLETADA
}

class Reservation {
    private var id: String = ""
    private var spaceId: String = ""
    private var personId: String = ""
    private var startDateTime: LocalDateTime = LocalDateTime.now()
    private var endDateTime: LocalDateTime = LocalDateTime.now()
    private var status: ReservationStatus = ReservationStatus.PENDIENTE
    private var purpose: String = ""
    private var numberOfAttendees: Int = 0
    private var totalPrice: Double = 0.0
    private var notes: String = ""

    constructor()

    constructor(
        id: String,
        spaceId: String,
        personId: String,
        startDateTime: LocalDateTime,
        endDateTime: LocalDateTime,
        status: ReservationStatus = ReservationStatus.PENDIENTE,
        purpose: String,
        numberOfAttendees: Int,
        totalPrice: Double = 0.0,
        notes: String = ""
    ) {
        this.id = id
        this.spaceId = spaceId
        this.personId = personId
        this.startDateTime = startDateTime
        this.endDateTime = endDateTime
        this.status = status
        this.purpose = purpose
        this.numberOfAttendees = numberOfAttendees
        this.totalPrice = totalPrice
        this.notes = notes
    }

    var ID: String
        get() = this.id
        set(value) { this.id = value }

    var SpaceId: String
        get() = this.spaceId
        set(value) { this.spaceId = value }

    var PersonId: String
        get() = this.personId
        set(value) { this.personId = value }

    var StartDateTime: LocalDateTime
        get() = this.startDateTime
        set(value) { this.startDateTime = value }

    var EndDateTime: LocalDateTime
        get() = this.endDateTime
        set(value) { this.endDateTime = value }

    var Status: ReservationStatus
        get() = this.status
        set(value) { this.status = value }

    var Purpose: String
        get() = this.purpose
        set(value) { this.purpose = value }

    var NumberOfAttendees: Int
        get() = this.numberOfAttendees
        set(value) { this.numberOfAttendees = value }

    var TotalPrice: Double
        get() = this.totalPrice
        set(value) { this.totalPrice = value }

    var Notes: String
        get() = this.notes
        set(value) { this.notes = value }

    fun getDurationInHours(): Long {
        return java.time.Duration.between(startDateTime, endDateTime).toHours()
    }

    override fun toString(): String {
        return "Reserva #$id - Estado: ${status.name}"
    }
}
