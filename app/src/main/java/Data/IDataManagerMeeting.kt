package Data

import Entity.Meeting

interface IDataManagerMeeting {
    fun add(meeting: Meeting)
    fun update(meeting: Meeting)
    fun remove(id: String)
    fun getAll(): List<Meeting>
    fun getById(id: String): Meeting?
    fun getByDate(date: String): List<Meeting>?
}
