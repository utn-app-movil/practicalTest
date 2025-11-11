package Data

import Entity.Person
import Entity.Volunteer
import Entity.Service

<<<<<<< Updated upstream
object MemoryDataManager: IDataManager, IDataManagerVolunteer{
=======
object MemoryDataManager: IDataManager{
>>>>>>> Stashed changes
    private  var personList = mutableListOf<Person>()
    private  var volunteerList = mutableListOf<Volunteer>()


    override fun add(person: Person) {
        personList.add(person)
    }

    override fun remove(id: String) {
        personList.removeIf { it.ID.trim() == id.trim() }
    }

    override fun update(person: Person) {
        remove(person.ID)
        add(person)
    }

    override fun getAll() = personList

    override fun getById(id: String): Person? {
        val result = personList.filter { it.ID.trim() == id.trim() }
        return if (result.any()) result[0] else null
    }

    override fun getByFullName(fullName: String): Person? {
        val result = personList.filter { it.FullName() == fullName.trim() }
        return if (result.any()) result[0] else null
    }


    private val serviceList = mutableListOf<Service>()

    fun addService(service: Service) {
        serviceList.add(service)
    }

    //HERE STARTS DE VOLUNTEER CONTROLLER OVERRIDES
    override fun addV(volunteer: Volunteer){
        volunteerList.add(volunteer)
    }

    override fun updateV(volunteer: Volunteer){
        remove(volunteer.ID)
        addV(volunteer)
    }

    override fun getVById(id: String): Volunteer?{
        val result = volunteerList.filter { it.ID.trim() == id.trim()}
        return if(result.any()) result[0] else null
    }

    override fun removeV(id: String){
        volunteerList.removeIf { it.ID.trim() == id.trim() }
    }

    fun getAllServices(): List<Service> = serviceList

    fun updateService(updated: Service) {
        val index = serviceList.indexOfFirst { it.id == updated.id }
        if (index != -1) {
            serviceList[index] = updated
        }
    }

    fun deleteService(id: Int) {
        serviceList.removeIf { it.id == id }
    }

    fun getServiceById(id: Int): Service? {
        val result = serviceList.filter { it.id == id }
        return if (result.any()) result[0] else null
    }
}
