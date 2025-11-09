package Data

import Entity.Tournament

object TournamentDataManager: ITournamentData {

    private var tournamentList = mutableListOf<Tournament>()

    override fun add(tournament: Tournament) {
        tournamentList.add(tournament)
    }

    override fun update(tournament: Tournament) {
        remove(tournament.ID)
        add(tournament)
    }

    override fun remove(id: String) {
        tournamentList.removeIf { it.ID.trim() == id.trim() }
    }

    override fun getById(id: String): Tournament? {
        val result = tournamentList.
            filter { it.ID.trim() == id.trim()}
        return if(result.any()) result[0] else null
    }
}