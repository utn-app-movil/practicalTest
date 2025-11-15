package Controller

import Data.MemoryVolunteeringActivityDataManager
import Entity.VolunteeringActivity

class VolunteeringActivityController {

    private val activityData = MemoryVolunteeringActivityDataManager

    fun addActivity(activity: VolunteeringActivity): Boolean {
        return activityData.add(activity)
    }

    fun updateActivity(activity: VolunteeringActivity): Boolean {
        return activityData.update(activity)
    }

    fun deleteActivity(id: String): Boolean {
        return activityData.remove(id)
    }

    fun getAllActivities(): List<VolunteeringActivity> {
        return activityData.getAll()
    }

    fun getActivityById(id: String): VolunteeringActivity? {
        return activityData.getById(id)
    }

    fun getActivityByTitle(title: String): VolunteeringActivity? {
        return activityData.getByTitle(title)
    }
}