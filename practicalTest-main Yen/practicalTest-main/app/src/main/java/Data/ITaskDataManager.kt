<<<<<<< HEAD
package Data

import Entity.Task

interface ITaskDataManager {
    fun add(task: Task)
    fun update(task: Task)
    fun remove(id: String)
    fun getAll(): List<Task>
    fun getById(id: String): Task?
}
=======
package Data

import Entity.Task

interface ITaskDataManager {
    fun add(task: Task)
    fun update(task: Task)
    fun remove(id: String)
    fun getAll(): List<Task>
    fun getById(id: String): Task?
}
>>>>>>> 5b3db453399a7a47d6af935620f873db3690fa42
