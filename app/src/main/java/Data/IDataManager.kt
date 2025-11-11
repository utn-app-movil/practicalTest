package Data

import Entity.Person
import Entity.Product

interface IDataManager {
    fun add (person: Person)
    fun update (person: Person)
    fun remove (id: String)
    fun getAll(): List<Person>
    fun getById(id: String): Person?
    fun getByFullName(fullName: String): Person?

}