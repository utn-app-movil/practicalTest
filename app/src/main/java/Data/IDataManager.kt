package Data

<<<<<<< HEAD
import Entity.Identifier
import Entity.Person

interface IDataManager {
    fun add (person: Identifier)
    fun update (person: Identifier)
    fun remove (id: String)
    fun getAll(): List<Identifier>
    fun getById(id: String): Identifier?
    fun getByFullName(fullName: String): Identifier?
=======
import Entity.Person

interface IDataManager {
    fun add (person: Person)
    fun update (person: Person)
    fun remove (id: String)
    fun getAll(): List<Person>
    fun getById(id: String): Person?
    fun getByFullName(fullName: String): Person?
>>>>>>> origin/dev-team2
}