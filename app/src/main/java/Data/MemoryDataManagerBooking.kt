package Data

import Entity.Booking

object MemoryDataManagerBooking: IDataManagerBooking {

    private var bookingList = mutableListOf<Booking>()

    override fun add(booking: Booking) {
        bookingList.add(booking)
    }

    override fun remove(id: String) {
        bookingList.removeIf { it.IDBooking.trim() == id.trim() }
    }

    override fun update(booking: Booking) {
        remove(booking.IDBooking)
        add(booking)
    }

    override fun getAll()= bookingList

    override fun getById(id: String): Booking? {
        val result = bookingList.
            filter { it.IDBooking.trim() == id.trim()}
        return if(result.any()) result[0] else null
    }

}