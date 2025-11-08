package Data

import Entity.Identifier
import Entity.Person

interface IDataManager {
    fun add (person: Identifier)
    fun update (person: Identifier)
    fun remove (id: String)
    fun getAll(): List<Identifier>
    fun getById(id: String): Identifier?
    fun getByFullName(fullName: String): Identifier?
}