package Entity

class Schedule {
    private var id: String = ""
    private var day: String = ""
    private var startTime: String = ""
    private var endTime: String = ""
    private var location: String = ""

    constructor()

    constructor(id: String, day: String, startTime: String, endTime: String, location: String) {
        this.id = id
        this.day = day
        this.startTime = startTime
        this.endTime = endTime
        this.location = location
    }

    var ID: String
        get() = this.id
        set(value) { this.id = value }

    var Day: String
        get() = this.day
        set(value) { this.day = value }

    var StartTime: String
        get() = this.startTime
        set(value) { this.startTime = value }

    var EndTime: String
        get() = this.endTime
        set(value) { this.endTime = value }

    var Location: String
        get() = this.location
        set(value) { this.location = value }

    fun ScheduleInfo(): String = "$day de $startTime a $endTime en $location"
}