<<<<<<< HEAD
package Entity

import java.time.LocalDate

/**
 * Represents a task assigned to a user inside the in-memory catalog.
 */
data class Task(
    var id: String,
    var title: String,
    var description: String,
    var assignedTo: String,
    var dueDate: LocalDate,
    var status: TaskStatus
)
=======
package Entity

import java.time.LocalDate

/**
 * Represents a task assigned to a user inside the in-memory catalog.
 */
data class Task(
    var id: String,
    var title: String,
    var description: String,
    var assignedTo: String,
    var dueDate: LocalDate,
    var status: TaskStatus
)
>>>>>>> 5b3db453399a7a47d6af935620f873db3690fa42
