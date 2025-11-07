package cr.ac.utn.practicaltest.Data

import cr.ac.utn.practicaltest.Entity.Team
import cr.ac.utn.practicaltest.Entity.Tournament
import cr.ac.utn.practicaltest.Entity.Training

/**
 * Repositorio en memoria para la pantalla Entertainment.
 * No interfiere con los repos/DM del profesor.
 */
object EntertainmentMemoryRepository : IEntertainmentRepository {

    private val tournaments = mutableListOf<Tournament>()
    private val trainings   = mutableListOf<Training>()
    private val teams       = mutableListOf<Team>()

    private var tournamentId = 1
    private var trainingId   = 1
    private var teamId       = 1

    // --- Torneos ---
    override fun addTournament(item: Tournament): Boolean {
        if (tournaments.any { it.name.equals(item.name, true) }) return false
        tournaments.add(item.copy(id = tournamentId++))
        return true
    }
    override fun updateTournament(item: Tournament): Boolean {
        val idx = tournaments.indexOfFirst { it.id == item.id }
        if (idx == -1) return false
        if (tournaments.any { it.name.equals(item.name, true) && it.id != item.id }) return false
        tournaments[idx] = item; return true
    }
    override fun deleteTournament(id: Int): Boolean = tournaments.removeIf { it.id == id }
    override fun getTournaments(): List<Tournament> = tournaments.toList()
    override fun getTournamentById(id: Int): Tournament? = tournaments.find { it.id == id }

    // --- Entrenamientos ---
    override fun addTraining(item: Training): Boolean {
        if (trainings.any { it.name.equals(item.name, true) }) return false
        trainings.add(item.copy(id = trainingId++))
        return true
    }
    override fun updateTraining(item: Training): Boolean {
        val idx = trainings.indexOfFirst { it.id == item.id }
        if (idx == -1) return false
        if (trainings.any { it.name.equals(item.name, true) && it.id != item.id }) return false
        trainings[idx] = item; return true
    }
    override fun deleteTraining(id: Int): Boolean = trainings.removeIf { it.id == id }
    override fun getTrainings(): List<Training> = trainings.toList()
    override fun getTrainingById(id: Int): Training? = trainings.find { it.id == id }

    // --- Equipos ---
    override fun addTeam(item: Team): Boolean {
        if (teams.any { it.name.equals(item.name, true) }) return false
        teams.add(item.copy(id = teamId++))
        return true
    }
    override fun updateTeam(item: Team): Boolean {
        val idx = teams.indexOfFirst { it.id == item.id }
        if (idx == -1) return false
        if (teams.any { it.name.equals(item.name, true) && it.id != item.id }) return false
        teams[idx] = item; return true
    }
    override fun deleteTeam(id: Int): Boolean = teams.removeIf { it.id == id }
    override fun getTeams(): List<Team> = teams.toList()
    override fun getTeamById(id: Int): Team? = teams.find { it.id == id }
}
