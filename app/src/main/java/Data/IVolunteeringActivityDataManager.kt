package Data

import Entity.VolunteeringActivity

interface IVolunteeringActivityDataManager {
    fun add(activity: VolunteeringActivity): Boolean
    fun update(activity: VolunteeringActivity): Boolean
    fun remove(id: String): Boolean
    fun getAll(): List<VolunteeringActivity>
    fun getById(id: String): VolunteeringActivity?
    fun getByTitle(title: String): VolunteeringActivity?
}