package Data

import Entity.Volunteer


object MemoryDataManagerVolunteer: IDataManagerVolunteer {
    private  var volunteerList = mutableListOf<Volunteer>()

    override fun addV(volunteer: Volunteer){
        volunteerList.add(volunteer)
    }

    override fun updateV(volunteer: Volunteer){
        removeV(volunteer.ID)
        addV(volunteer)
    }

    override fun getVById(id: String): Volunteer?{
        val result = volunteerList.filter { it.ID.trim() == id.trim()}
        return if(result.any()) result[0] else null
    }

    override fun removeV(id: String){
        volunteerList.removeIf { it.ID.trim() == id.trim() }
    }
}