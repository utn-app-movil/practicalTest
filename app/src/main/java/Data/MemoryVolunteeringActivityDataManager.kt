package Data

import Entity.VolunteeringActivity

object MemoryVolunteeringActivityDataManager : IVolunteeringActivityDataManager {

    private val activities = mutableListOf<VolunteeringActivity>()

    override fun add(activity: VolunteeringActivity): Boolean {
        return try {
            activities.add(activity)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun update(activity: VolunteeringActivity): Boolean {
        val index = activities.indexOfFirst { it.ID == activity.ID }
        return if (index != -1) {
            activities[index] = activity
            true
        } else false
    }

    override fun remove(id: String): Boolean {
        return activities.removeIf { it.ID == id }
    }

    override fun getAll(): List<VolunteeringActivity> = activities

    override fun getById(id: String): VolunteeringActivity? =
        activities.firstOrNull { it.ID == id }

    override fun getByTitle(title: String): VolunteeringActivity? =
        activities.firstOrNull { it.Title.equals(title, ignoreCase = true) }
}