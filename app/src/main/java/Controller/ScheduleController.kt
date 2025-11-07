package Controller

import Data.IData
import Data.MemoryData
import Entity.Schedule
import android.content.Context
import cr.ac.utn.practicaltest.R

class ScheduleController {
    private var dataManager: IData = MemoryData
    private var context: Context

    constructor(context: Context){
        this.context=context
    }

        fun addSchedule(schedule: Schedule){
            try {
                dataManager.addSchedule(schedule)
            }catch (e: Exception){
                throw Exception(context
                    .getString(R.string.ErrorMsgAdd))
            }
        }

        fun updateSchedule(schedule: Schedule){
            try {
                dataManager.updateSchedule(schedule)
            }catch (e: Exception){
                throw Exception(context
                    .getString(R.string.ErrorMsgUpdate))
            }
        }

        fun getByIdSchedule(id: String): Schedule?{
            try {
                return dataManager.getByIdSchedule(id)
            }catch (e: Exception){
                throw Exception(context
                    .getString(R.string.ErrorMsgGetById))
            }
        }

        fun getByScheduleName(scheduleName: String): Schedule?{
            try {
                return dataManager.
                getByIdSchedule(scheduleName)
            }catch (e: Exception){
                throw Exception(context
                    .getString(R.string.ErrorMsgGetById))
            }
        }

        fun removeSchedule(id: String){
            try{
                val result = dataManager.getByIdSchedule(id)
                if (result == null){
                    throw Exception(context
                        .getString(R.string.MsgDataNoFound))
                }
                dataManager.removeSchedule(id)
            }catch (e: Exception){
                throw Exception(context
                    .getString(R.string.ErrorMsgRemove))
            }
        }
}