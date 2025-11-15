package Controller

import Data.MemoryScheduleDataManager
import Entity.Schedule

class ScheduleController {

    private val scheduleData = MemoryScheduleDataManager

    fun addSchedule(schedule: Schedule): Boolean {
        return scheduleData.add(schedule)
    }

    fun updateSchedule(schedule: Schedule): Boolean {
        return scheduleData.update(schedule)
    }

    fun deleteSchedule(id: String): Boolean {
        return scheduleData.remove(id)
    }

    fun getAllSchedules(): List<Schedule> {
        return scheduleData.getAll()
    }

    fun getScheduleById(id: String): Schedule? {
        return scheduleData.getById(id)
    }
}