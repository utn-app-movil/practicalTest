package Entity

class Inscription {
    private var InscriptionId:  String  =   ""
    private var PersonId:       String  =   ""
    private var CourseId:       String  =   ""

    constructor(InscriptionId: String, PersonId: String, CourseId: String)
    {
        this.InscriptionId  = InscriptionId
        this.PersonId       = PersonId
        this.CourseId       = CourseId
    }

    var idInscription: String
        get() = this.InscriptionId
        set(value) {this.InscriptionId=value}

    var idPerson: String
        get() = this.idPerson
        set(value) {this.idPerson=value}

    var idCourse: String
        get() = this.idCourse
        set(value) {this.idCourse=value}
}