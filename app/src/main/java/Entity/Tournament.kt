package Entity

import java.time.LocalDate

class Tournament {
    private var id: String = ""
    private var title: String = ""
    private var description: String = ""
    private lateinit var date: LocalDate
    private var prize: String = ""

    constructor()

    constructor(id: String, title: String, description: String, date: LocalDate, prize: String){
        this.id=id
        this.title=title
        this.description=description
        this.date=date
        this.prize=prize
    }

    var ID: String
        get() = this.id
        set(value) {this.id=value}

    var Title: String
        get() = this.title
        set(value) {this.title=value}

    var Description: String
        get() = this.description
        set(value) {this.description=value}

    var Date: LocalDate
        get() = this.date
        set(value) {this.date=value}

    var Prize: String
        get() = this.prize
        set(value) {this.prize=value}

}