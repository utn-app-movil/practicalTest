package Entity

import java.time.LocalDate
import java.time.LocalTime

class Event {
    var id: String = ""
    var title: String = ""
    var description: String = ""
    var date: LocalDate? = null
    var time: LocalTime? = null
    var location: String = ""
    var organizer: String = ""

    constructor()

    constructor(
        id: String, title: String, description: String,
        date: LocalDate?, time: LocalTime?,
        location: String, organizer: String
    ) {
        this.id = id
        this.title = title
        this.description = description
        this.date = date
        this.time = time
        this.location = location
        this.organizer = organizer
    }

    fun fullInfo(): String = "$title - $location"
}