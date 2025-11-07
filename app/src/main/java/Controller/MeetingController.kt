package Controller

import Data.IDataManagerMeeting
import Data.MemoryDataManagerMeeting
import Entity.Meeting
import android.content.Context
import cr.ac.utn.practicaltest.R

class MeetingController {

    private var dataManager: IDataManagerMeeting = MemoryDataManagerMeeting
    private var context: Context

    constructor(context: Context) {
        this.context = context
    }

    fun addMeeting(meeting: Meeting) {
        try {
            dataManager.add(meeting)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgAdd))
        }
    }

    fun updateMeeting(meeting: Meeting) {
        try {
            dataManager.update(meeting)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgUpdate))
        }
    }

    fun getById(id: String): Meeting? {
        try {
            return dataManager.getById(id)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgGetById))
        }
    }

    fun getByDate(date: String): List<Meeting>? {
        try {
            return dataManager.getByDate(date)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgGetById))
        }
    }

    fun removeMeeting(id: String) {
        try {
            val result = dataManager.getById(id)
            if (result == null) {
                throw Exception(context.getString(R.string.MsgDataNoFound))
            }
            dataManager.remove(id)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgRemove))
        }
    }

    fun getAllMeetings(): List<Meeting> {
        return dataManager.getAll()
    }
}
