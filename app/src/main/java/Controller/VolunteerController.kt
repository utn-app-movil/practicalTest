package Controller

import Data.IDataManagerVolunteer
import Data.MemoryDataManager
import Entity.Volunteer
import android.content.Context
import cr.ac.utn.practicaltest.R

class VolunteerController {
    private var dataManager: IDataManagerVolunteer = MemoryDataManager
    private  var context: Context

    constructor(context: Context){
        this.context=context
    }

    fun addVolunteer(volunteer: Volunteer){
        try {
            dataManager.addV(volunteer)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgAdd))
        }
    }

    fun updateVolunteer(volunteer: Volunteer){
        try {
            dataManager.updateV(volunteer)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgUpdate))
        }
    }

    fun removeVolunteer(id: String){
        try{
            val result = dataManager.getVById(id)
            if (result == null){
                throw Exception(context
                    .getString(R.string.MsgDataNoFound))
            }
            dataManager.removeV(id)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgRemove))
        }
    }

<<<<<<< Updated upstream
fun getVolunteerById(id: String): Volunteer?{
=======
    fun getVolunteerById(id: String): Volunteer?{
>>>>>>> Stashed changes
        try {
            return dataManager.getVById(id)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgGetById))
        }
    }

}