package Data

import Entity.Meeting

object MemoryDataManagerMeeting : IDataManagerMeeting {
    private var meetingList = mutableListOf<Meeting>()

    override fun add(meeting: Meeting) {
        meetingList.add(meeting)
    }

    override fun remove(id: String) {
        meetingList.removeIf { it.ID.trim() == id.trim() }
    }

    override fun update(meeting: Meeting) {
        remove(meeting.ID)
        add(meeting)
    }

    override fun getAll() = meetingList

    override fun getById(id: String): Meeting? {
        val result = meetingList.filter { it.ID.trim() == id.trim() }
        return if (result.any()) result[0] else null
    }

    override fun getByDate(date: String): List<Meeting>? {
        val result = meetingList.filter { it.Date.trim() == date.trim() }
        return if (result.any()) result else null
    }
}
