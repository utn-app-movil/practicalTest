package Data

import Entity.Identifier

object MemoryDataManager: IDataManager {
    private  var personList = mutableListOf<Identifier>()
    override fun add(person: Identifier) {
        personList.add(person)
    }

    override fun remove(id: String) {
        personList.removeIf { it.ID.trim() == id.trim() }
    }

    override fun update(person: Identifier) {
        remove(person.ID)
        add(person)
    }

    override fun getAll()= personList

    override fun getById(id: String): Identifier? {
        val result = personList.
        filter { it.ID.trim() == id.trim()}
        return if(result.any()) result[0] else null
    }

    override fun getByFullName(fullName: String): Identifier? {
        val result = personList.
        filter { it.FullName == fullName.trim()}
        return if(result.any()) result[0] else null
    }

}