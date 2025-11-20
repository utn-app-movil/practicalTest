package Data

import Entity.Author
import Entity.Book
import Entity.Loan
import Entity.Person

object MemoryDataManager: IDataManager {
    private  var personList = mutableListOf<Person>()
    private val booksList = mutableListOf<Book>()
    private val authorsList = mutableListOf<Author>()
    private val loansList = mutableListOf<Loan>()
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

    override fun getAll()= personList

    override fun getById(id: String): Person? {
        val result = personList.
            filter { it.ID.trim() == id.trim()}
        return if(result.any()) result[0] else null
    }

    override fun getByFullName(fullName: String): Person? {
        val result = personList.
        filter { it.FullName() == fullName.trim()}
        return if(result.any()) result[0] else null
    }

}