package Entity

import java.time.LocalDate
import java.util.Date

class Volunteer {
    private lateinit var id: String
    private lateinit var name: String
    private lateinit var date: LocalDate
    private lateinit var hour: String
    private lateinit var idPerson: String

    var personList = mutableListOf<Person>()

    constructor()

    constructor(id: String, name: String, date: LocalDate, hour: String, idPerson: String)
    {
        this.id=id
        this.name=name
        this.date=date
        this.hour=hour
        this.idPerson=idPerson
    }

    var ID: String
        get() = this.id
        set(value) {this.id=value}

    var Name: String
        get() = this.name
        set(value) {this.name=value}

    var Date: LocalDate
        get() = this.date
        set(value) {this.date=value}

    var Hour: String
        get() = this.hour
        set(value) {this.hour=value}

    var IdPerson: String
        get() = this.idPerson
        set(value) {this.idPerson=value}
        
}