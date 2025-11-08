package Entity

import java.time.LocalDate

class Participant {

    private var id: String = ""
    private var name: String = ""

    private var fLastName: String=""
    private var sLastName: String=""
    private var email: String = ""
    private var phone: Int = 0
    private lateinit var birthday: LocalDate


    constructor()

    constructor(id: String, name: String, flastname: String
                , slastname: String, email: String, phone: Int, birthday: LocalDate) {
        this.id = id
        this.name = name
        this.fLastName=flastname
        this.sLastName=slastname
        this.email = email
        this.phone = phone
        this.birthday = birthday


    }

    var Id: String
        get() = this.id
        set(value) { this.id = value }

    var Name: String
        get() = this.name
        set(value) { this.name = value }

    var FLastName: String
        get() = this.fLastName
        set(value) {this.fLastName=value}

    var SLastName: String
        get() = this.sLastName
        set(value) {this.sLastName=value}

    var Email: String
        get() = this.email
        set(value) { this.email = value }

    var Phone: Int
        get() = this.phone
        set(value) { this.phone = value }

    var Birthday: LocalDate
        get() = this.birthday
        set(value) { this.birthday = value }



    fun FullName() = "$this.name $this.fLastName $this.sLastName"
}