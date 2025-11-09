package Data

<<<<<<< HEAD
import Entity.Identifier

object MemoryDataManager: IDataManager {
    private  var personList = mutableListOf<Identifier>()
    override fun add(person: Identifier) {
=======
import Entity.Person

object MemoryDataManager: IDataManager {
    private  var personList = mutableListOf<Person>()
    override fun add(person: Person) {
>>>>>>> origin/dev-team2
        personList.add(person)
    }

    override fun remove(id: String) {
        personList.removeIf { it.ID.trim() == id.trim() }
    }

<<<<<<< HEAD
    override fun update(person: Identifier) {
=======
    override fun update(person: Person) {
>>>>>>> origin/dev-team2
        remove(person.ID)
        add(person)
    }

    override fun getAll()= personList

<<<<<<< HEAD
    override fun getById(id: String): Identifier? {
=======
    override fun getById(id: String): Person? {
>>>>>>> origin/dev-team2
        val result = personList.
            filter { it.ID.trim() == id.trim()}
        return if(result.any()) result[0] else null
    }

<<<<<<< HEAD
    override fun getByFullName(fullName: String): Identifier? {
        val result = personList.
        filter { it.FullName == fullName.trim()}
=======
    override fun getByFullName(fullName: String): Person? {
        val result = personList.
        filter { it.FullName() == fullName.trim()}
>>>>>>> origin/dev-team2
        return if(result.any()) result[0] else null
    }

}