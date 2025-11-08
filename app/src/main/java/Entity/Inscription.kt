package Entity

class Inscription {
    var InscriptionId: String = ""
    var PersonId: String = ""
    var PersonEmail: String = ""
    var CourseName: String = ""
    var TrainingName: String = ""
    var Schedule: String = ""

    constructor()

    constructor(InscriptionId: String, PersonId: String, PersonEmail: String, CourseName: String, TrainingName: String, Schedule: String) {
        this.InscriptionId = InscriptionId
        this.PersonId = PersonId
        this.PersonEmail = PersonEmail
        this.CourseName = CourseName
        this.TrainingName = TrainingName
        this.Schedule = Schedule
    }

    var idInscription: String
        get() = this.InscriptionId
        set(value) { this.InscriptionId = value }

    var idPerson: String
        get() = this.PersonId
        set(value) { this.PersonId = value }
}
