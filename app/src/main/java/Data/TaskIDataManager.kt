package Data

import Entity.Task

interface TaskIDataManager {
    fun addTask(task: Task) {
        TaskIDataManager.tasks.(task)
    }

    fun getAllTasks(): List<Task> {
        return TaskeMemoryManager.tasks
    }

    fun updateTask(id: Int, newName: String, newDescription: String) {
        val task = TaskeMemoryManager.tasks.find { it.getId() == id }
        task?.apply {
            setName(newName)
            setDescription(newDescription)
        }
    }

    fun deleteTask(id: Int) {
        val task = TaskeMemoryManager.tasks.find { it.getId() == id }
        if (task != null) {
            TaskeMemoryManager.tasks.remove(task)
        }
    }

    companion object {
        val tasks: Any
            get() {
                TODO()
            }
    }

}


