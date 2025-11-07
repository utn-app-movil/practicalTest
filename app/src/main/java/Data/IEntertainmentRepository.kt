package cr.ac.utn.practicaltest.Data

import cr.ac.utn.practicaltest.Entity.Team
import cr.ac.utn.practicaltest.Entity.Tournament
import cr.ac.utn.practicaltest.Entity.Training

interface IEntertainmentRepository {
    // Torneos
    fun addTournament(item: Tournament): Boolean
    fun updateTournament(item: Tournament): Boolean
    fun deleteTournament(id: Int): Boolean
    fun getTournaments(): List<Tournament>
    fun getTournamentById(id: Int): Tournament?

    // Entrenamientos
    fun addTraining(item: Training): Boolean
    fun updateTraining(item: Training): Boolean
    fun deleteTraining(id: Int): Boolean
    fun getTrainings(): List<Training>
    fun getTrainingById(id: Int): Training?

    // Equipos
    fun addTeam(item: Team): Boolean
    fun updateTeam(item: Team): Boolean
    fun deleteTeam(id: Int): Boolean
    fun getTeams(): List<Team>
    fun getTeamById(id: Int): Team?
}
