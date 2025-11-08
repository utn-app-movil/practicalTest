package Data

import Entity.Booking

interface IDataManagerBooking {
    fun add (booking: Booking)
    fun update (booking: Booking)
    fun remove (id: String)
    fun getAll(): List<Booking>
    fun getById(id: String): Booking?
}