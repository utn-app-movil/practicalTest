package Data

import Entity.Task

object TaskMemoryManager : TaskIDataManager {

    private val taskList = mutableListOf<Task>()
    private var counter = 1

    override fun addTask(title: String, description: String, user: String) {
        val task = Task(counter++, title, description, user)
        taskList.add(task)
    }

    override fun getAllTasks(): MutableList<Task> = taskList

    override fun updateTask(id: Int, title: String, description: String, user: String) {
        val task = taskList.find { it.id == id }
        task?.apply {
            this.title = title
            this.description = description
            this.user = user
        }
    }

    override fun deleteTask(id: Int) {
        taskList.removeIf { it.id == id }
    }
}

