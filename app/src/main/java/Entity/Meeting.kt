package Entity

class Meeting {
    private var id: String = ""
    private var date: String = ""
    private var time: String = ""
    private var participants: String = ""

    constructor()

    constructor(id: String, date: String, time: String, participants: String) {
        this.id = id
        this.date = date
        this.time = time
        this.participants = participants
    }

    var ID: String
        get() = this.id
        set(value) { this.id = value }

    var Date: String
        get() = this.date
        set(value) { this.date = value }

    var Time: String
        get() = this.time
        set(value) { this.time = value }

    var Participants: String
        get() = this.participants
        set(value) { this.participants = value }


    fun MeetingInfo(): String {
        return "Date: $date, Time: $time, Participants: $participants"
    }
}
