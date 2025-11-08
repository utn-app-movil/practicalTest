package Data

import Entity.Schedule

object MemoryScheduleDataManager : IScheduleDataManager {

    private val schedules = mutableListOf<Schedule>()

    override fun add(schedule: Schedule): Boolean {
        return try {
            schedules.add(schedule)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun update(schedule: Schedule): Boolean {
        val index = schedules.indexOfFirst { it.ID == schedule.ID }
        return if (index != -1) {
            schedules[index] = schedule
            true
        } else false
    }

    override fun remove(id: String): Boolean {
        return schedules.removeIf { it.ID == id }
    }

    override fun getAll(): List<Schedule> = schedules

    override fun getById(id: String): Schedule? =
        schedules.firstOrNull { it.ID == id }
}