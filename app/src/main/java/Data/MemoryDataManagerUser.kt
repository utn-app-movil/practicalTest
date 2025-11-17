package Data

import Entity.User

object MemoryDataManagerUser: IDataManagerUser {
    private  var userList = mutableListOf<User>()
    override fun add(user: User) {
        userList.add(user)
    }

    override fun remove(id: String) {
        userList.removeIf { it.ID.trim() == id.trim() }
    }

    override fun update(user: User) {
        remove(user.ID)
        add(user)
    }

    override fun getAll()= userList

    override fun getById(id: String): User? {
        val result = userList.
            filter { it.ID.trim() == id.trim()}
        return if(result.any()) result[0] else null
    }
}