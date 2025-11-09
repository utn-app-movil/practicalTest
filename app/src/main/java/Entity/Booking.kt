package Entity

import java.time.LocalDateTime

class Booking {

    private var idBooking = ""
    private var spaceName = "" // such as "Cancha de Basketball"
    private var spaceType = "" // type of space being booked, such as "Cancha"
    private var bookingDateTime: LocalDateTime = LocalDateTime.now()
    private var bookingEndDateTime: LocalDateTime = LocalDateTime.now()
    private var status = "" // confirmed , pending, canceled
    private var person: Person? = null

    constructor()

    constructor(idBooking: String, spaceName: String, spaceType: String,
                bookingDateTime: LocalDateTime, bookingEndDateTime: LocalDateTime, status: String, person: Person?) {
        this.idBooking = idBooking
        this.spaceName = spaceName
        this.spaceType = spaceType
        this.bookingDateTime = bookingDateTime
        this.bookingEndDateTime = bookingEndDateTime
        this.status = status
        this.person = person
    }

    var IDBooking: String
        get() = this.idBooking
        set(value) { this.idBooking = value }

    var SpaceName: String
        get() = this.spaceName
        set(value) { this.spaceName = value }

    var SpaceType: String
        get() = this.spaceType
        set(value) { this.spaceType = value }

    var BookingDateTime: LocalDateTime
        get() = this.bookingDateTime
        set(value) { this.bookingDateTime = value }

    var BookingEndDateTime: LocalDateTime
        get() = this.bookingEndDateTime
        set(value) { this.bookingEndDateTime = value }

    var Status: String
        get() = this.status
        set(value) { this.status = value }

    var Person: Person?
        get() = this.person
        set(value) { this.person = value }

}