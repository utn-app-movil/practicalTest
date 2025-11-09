package Data

import Entity.Tournament

interface ITournamentData {
    fun add(tournament: Tournament)
    fun update(tournament: Tournament)
    fun remove(id: String)
    fun getById(id: String): Tournament?
}