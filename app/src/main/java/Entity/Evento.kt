package Entity

import java.time.LocalDate
import java.time.LocalTime

class Evento {
    var id: String = ""
    var title: String = ""
    var description: String = ""
    var date: LocalDate? = null
    var time: LocalTime? = null
    var location: String = ""
    var organizer: String = ""

    constructor()
    constructor(
        id: String, title: String, desc: String,
        date: LocalDate?, time: LocalTime?,
        loc: String, org: String
    ) {
        this.id = id
        this.title = title
        this.description = desc
        this.date = date
        this.time = time
        this.location = loc
        this.organizer = org
    }

    fun fullInfo(): String = "$title - $location"
}