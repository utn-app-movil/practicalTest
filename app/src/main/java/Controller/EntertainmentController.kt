package cr.ac.utn.practicaltest.Controller

import cr.ac.utn.practicaltest.Data.IEntertainmentRepository
import cr.ac.utn.practicaltest.Entity.Team
import cr.ac.utn.practicaltest.Entity.Tournament
import cr.ac.utn.practicaltest.Entity.Training
import cr.ac.utn.practicaltest.R
import cr.ac.utn.practicaltest.Util.Result

class EntertainmentController(private val repo: IEntertainmentRepository) {

    // Tournaments
    fun saveTournament(id: Int?, name: String, description: String): Result<Unit> {
        if (name.isBlank()) return Result.Error(R.string.ErrorMsgAdd)
        val ok = if (id == null) repo.addTournament(Tournament(0, name.trim(), description.trim()))
        else repo.updateTournament(Tournament(id, name.trim(), description.trim()))
        return if (ok) Result.Success(Unit) else Result.Error(R.string.MsgDuplicateDate)
    }
    fun deleteTournament(id: Int): Result<Unit> =
        if (repo.deleteTournament(id)) Result.Success(Unit) else Result.Error(R.string.ErrorMsgRemove)
    fun listTournaments() = repo.getTournaments()

    // Trainings
    fun saveTraining(id: Int?, name: String, description: String): Result<Unit> {
        if (name.isBlank()) return Result.Error(R.string.ErrorMsgAdd)
        val ok = if (id == null) repo.addTraining(Training(0, name.trim(), description.trim()))
        else repo.updateTraining(Training(id, name.trim(), description.trim()))
        return if (ok) Result.Success(Unit) else Result.Error(R.string.MsgDuplicateDate)
    }
    fun deleteTraining(id: Int): Result<Unit> =
        if (repo.deleteTraining(id)) Result.Success(Unit) else Result.Error(R.string.ErrorMsgRemove)
    fun listTrainings() = repo.getTrainings()

    // Teams
    fun saveTeam(id: Int?, name: String, description: String): Result<Unit> {
        if (name.isBlank()) return Result.Error(R.string.ErrorMsgAdd)
        val ok = if (id == null) repo.addTeam(Team(0, name.trim(), description.trim()))
        else repo.updateTeam(Team(id, name.trim(), description.trim()))
        return if (ok) Result.Success(Unit) else Result.Error(R.string.MsgDuplicateDate)
    }
    fun deleteTeam(id: Int): Result<Unit> =
        if (repo.deleteTeam(id)) Result.Success(Unit) else Result.Error(R.string.ErrorMsgRemove)
    fun listTeams() = repo.getTeams()
}
