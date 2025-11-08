package Entity

class Task(
    private var id: Int,
    private var name: String,
    private var description: String,
    private var completed: Boolean = false,
    private var assignedUser: String? = null
) {
    fun getId() = id
    fun getName() = name
    fun getDescription() = description
    fun isCompleted() = completed
    fun getAssignedUser() = assignedUser

    fun setName(newName: String) { name = newName }
    fun setDescription(newDescription: String) { description = newDescription }
    fun setCompleted(isCompleted: Boolean) { completed = isCompleted }
    fun setAssignedUser(user: String?) { assignedUser = user }

    override fun toString(): String {
        return "Task(id=$id, name='$name', description='$description', completed=$completed, assignedUser=$assignedUser)"
    }
}
