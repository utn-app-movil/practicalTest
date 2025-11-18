package Controller

import Data.ITournamentData
import Data.TournamentDataManager
import Entity.Tournament
import android.content.Context
import cr.ac.utn.practicaltest.R

class TournamentController {
    private var dataManager: ITournamentData = TournamentDataManager
    private  var context: Context

    constructor(context: Context){
        this.context=context
    }

    fun addTournament(tournament: Tournament){
        try {
            dataManager.add(tournament)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgAdd))
        }
    }

    fun updateTournament(tournament: Tournament){
        try {
            dataManager.update(tournament)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgUpdate))
        }
    }

    fun getById(id: String): Tournament?{
        try {
            return dataManager.getById(id)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgGetById))
        }
    }

    fun removeTournament(id: String){
        try{
            val result = dataManager.getById(id)
            if (result == null){
                throw Exception(context
                    .getString(R.string.MsgDataNoFound))
            }
            dataManager.remove(id)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgRemove))
        }
    }
}