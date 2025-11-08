package Entity

class VolunteeringActivity {
    private var id: String = ""
    private var title: String = ""
    private var description: String = ""
    private var participants: MutableList<Participant> = mutableListOf()
    private lateinit var schedule: Schedule

    constructor()

    constructor(id: String, title: String, description: String, schedule: Schedule, participants: MutableList<Participant>) {
        this.id = id
        this.title = title
        this.description = description
        this.schedule = schedule
        this.participants = participants
    }

    var ID: String
        get() = this.id
        set(value) { this.id = value }

    var Title: String
        get() = this.title
        set(value) { this.title = value }

    var Description: String
        get() = this.description
        set(value) { this.description = value }

    var Schedule: Schedule
        get() = this.schedule
        set(value) { this.schedule = value }

    var Participants: MutableList<Participant>
        get() = this.participants
        set(value) { this.participants = value }

    fun ActivityInfo(): String = "$title - ${schedule.ScheduleInfo()}"
}