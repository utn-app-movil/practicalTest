package Data

import Entity.User

interface IDataManagerUser {
    fun add (user: User)
    fun update (user: User)
    fun remove (id: String)
    fun getAll(): List<User>
    fun getById(id: String): User?
}