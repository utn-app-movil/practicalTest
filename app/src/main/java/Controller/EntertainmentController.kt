package cr.ac.utn.practicaltest.Controller

import cr.ac.utn.practicaltest.Data.IDataManager
import cr.ac.utn.practicaltest.Entity.Team
import cr.ac.utn.practicaltest.Entity.Training
import cr.ac.utn.practicaltest.Entity.Tournament
import cr.ac.utn.practicaltest.R
import cr.ac.utn.practicaltest.Util.Result

class EntertainmentController(
    private val dm: IDataManager
) {
    // --- Torneos ---
    fun saveTournament(id: Int?, name: String, description: String): Result<Unit> {
        if (name.isBlank()) return Result.Error(R.string.ErrorMsgAdd)
        val ok = if (id == null) {
            dm.addTournament(Tournament(0, name.trim(), description.trim()))
        } else {
            dm.updateTournament(Tournament(id, name.trim(), description.trim()))
        }
        return if (ok) Result.Success(Unit) else Result.Error(R.string.MsgDuplicateDate)
    }

    fun deleteTournament(id: Int): Result<Unit> =
        if (dm.deleteTournament(id)) Result.Success(Unit) else Result.Error(R.string.ErrorMsgRemove)

    fun listTournaments() = dm.getTournaments()

    // --- Entrenamientos ---
    fun saveTraining(id: Int?, name: String, description: String): Result<Unit> {
        if (name.isBlank()) return Result.Error(R.string.ErrorMsgAdd)
        val ok = if (id == null) {
            dm.addTraining(Training(0, name.trim(), description.trim()))
        } else {
            dm.updateTraining(Training(id, name.trim(), description.trim()))
        }
        return if (ok) Result.Success(Unit) else Result.Error(R.string.MsgDuplicateDate)
    }

    fun deleteTraining(id: Int): Result<Unit> =
        if (dm.deleteTraining(id)) Result.Success(Unit) else Result.Error(R.string.ErrorMsgRemove)

    fun listTrainings() = dm.getTrainings()

    // --- Equipos ---
    fun saveTeam(id: Int?, name: String, description: String): Result<Unit> {
        if (name.isBlank()) return Result.Error(R.string.ErrorMsgAdd)
        val ok = if (id == null) {
            dm.addTeam(Team(0, name.trim(), description.trim()))
        } else {
            dm.updateTeam(Team(id, name.trim(), description.trim()))
        }
        return if (ok) Result.Success(Unit) else Result.Error(R.string.MsgDuplicateDate)
    }

    fun deleteTeam(id: Int): Result<Unit> =
        if (dm.deleteTeam(id)) Result.Success(Unit) else Result.Error(R.string.ErrorMsgRemove)

    fun listTeams() = dm.getTeams()
}
