package Entity

class Course {
    private var CourseId:   String  =   ""
    private var CourseName: String  =   ""
    private var ScheduleId: String  =   ""
    private var TrainingId: String  =   ""
    private var Available:  Boolean =   true


    constructor(CourseId: String, CourseName: String, ScheduleId: String, TrainingId: String, Available: Boolean)
    {
        this.CourseId  = CourseId
        this.CourseName       = CourseName
        this.ScheduleId       = ScheduleId
        this.TrainingId       = TrainingId
        this.Available       = Available
    }

    var idCourse: String
        get() = this.idCourse
        set(value) {this.idCourse=value}

    var Course_Name: String
        get() = this.Course_Name
        set(value) {this.Course_Name=value}

    var idSchedule: String
        get() = this.idSchedule
        set(value) {this.idSchedule=value}

    var bolAvailable: Boolean
        get() = this.bolAvailable
        set(value) {this.bolAvailable=value}
}