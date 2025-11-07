package Data

import Entity.MovieTicket

object MemoryDataManager : IDataManager {
    private var ticketList = mutableListOf<MovieTicket>()

    override fun add(ticket: MovieTicket) {
        ticketList.add(ticket)
    }

    override fun remove(id: String) {
        ticketList.removeIf { it.ID.trim() == id.trim() }
    }

    override fun update(ticket: MovieTicket) {
        remove(ticket.ID)
        add(ticket)
    }

    override fun getAll() = ticketList

    override fun getById(id: String): MovieTicket? {
        val result = ticketList.filter { it.ID.trim() == id.trim() }
        return if (result.any()) result[0] else null
    }
}