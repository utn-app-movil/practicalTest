package Data

import Entity.MovieTicket

interface IDataManager {
    fun add(ticket: MovieTicket)
    fun update(ticket: MovieTicket)
    fun remove(id: String)
    fun getAll(): List<MovieTicket>
    fun getById(id: String): MovieTicket?
}