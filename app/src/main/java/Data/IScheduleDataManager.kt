package Data

import Entity.Schedule

interface IScheduleDataManager {
    fun add(schedule: Schedule): Boolean
    fun update(schedule: Schedule): Boolean
    fun remove(id: String): Boolean
    fun getAll(): List<Schedule>
    fun getById(id: String): Schedule?
}