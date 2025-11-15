package Data

import Entity.Volunteer

interface IDataManagerVolunteer {
    fun addV(volunteer: Volunteer)
    fun updateV(volunteer: Volunteer)
    fun getVById(id: String): Volunteer?
    fun removeV(id: String)
}