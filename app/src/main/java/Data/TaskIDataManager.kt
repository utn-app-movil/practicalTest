package Data

import Entity.Task

interface TaskIDataManager {

    fun addTask(title: String, description: String, user: String)

    fun getAllTasks(): List<Task>

    fun updateTask(id: Int, title: String, description: String, user: String)

    fun deleteTask(id: Int)
}





